import React from 'react';
import { Menu, MenuItem, Avatar, AppBar, Toolbar, IconButton, Typography } from '@material-ui/core';
import { GoogleLogin, GoogleLogout } from 'react-google-login';
import SearchIcon from '@material-ui/icons/Search';
import { withStyles } from '@material-ui/core/styles';
import { GOOGLE_CLIENT_ID } from '../services/cte';
import User from '../models/User'
import { Link } from 'react-router-dom'

import Search from './Search';

class NavBar extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            anchorEl: null,
        };
    }

    handleProfileMenuOpen = event => {
        this.setState({ anchorEl: event.currentTarget });
    }

    handleMenuClose = () => {
        this.setState({ anchorrEl: null })
    }

    handleLogin = (response) => {
        const user = new User(
            response.profileObj.name,
            response.profileObj.email,
            response.profileObj.imageUrl
        );
        let gtoken = response.tokenId;
        this.props.login(gtoken, user)
    }

    handleLogout = () => {
        this.props.logout()
    }

    render() {
        const { anchorEl } = this.state;
        const { classes, logged, user } = this.props;
        const isMenuOpen = Boolean(anchorEl);

        const renderMenu = (
            <Menu
                anchorEl={anchorEl}
                anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                transformOOrigin={{ vertical: 'top', horizontal: 'right' }}
                open={isMenuOpen}
                onClose={this.handleMenuClose}
            >
                <MenuItem onClick={this.handleMenuClose}>
                    <Avatar component='img' alt='picture' src={user.imageUrl}></Avatar>
                </MenuItem>
                <MenuItem onClick={this.handleMenuClose}>My Account</MenuItem>
            </Menu>
        )

        return (
            <div className={classes.root}>
                <AppBar position='fixed'>
                    <Toolbar>
                        {user &&
                            <IconButton onClick={() => { alert(user.email) }}>
                                <Avatar alt='user portrait' src={user.imageUrl} />
                            </IconButton>
                        }
                        <Typography className={classes.title} component={Link} style={{textDecoration: 'none'}} to="/" variant="h6" color="inherit" noWrap>
                            Comic manager
                        </Typography>
                        <Search />
                        <div className={classes.grow} />
                        {
                            !logged &&
                            <GoogleLogin
                                clientId={GOOGLE_CLIENT_ID}
                                onSuccess={this.handleLogin}
                                onFailure={this.responseGoogle}
                            />
                        }
                        {
                            logged &&
                            <GoogleLogout
                                onLogoutSuccess={this.handleLogout}
                            />
                        }
                    </Toolbar>
                </AppBar>
                {renderMenu}
            </div>
        )
    }
}

const styles = theme => ({
    root: {
        width: '100%',
        marginBottom: theme.spacing.unit * 10,
    },
    grow: {
        flexGrow: 1,
    },
    menuButton: {
        marginLeft: -12,
        marginRight: 20,
    },
    title: {
        display: 'none',
        [theme.breakpoints.up('sm')]: {
            display: 'block',
        },
    },

    sectionDesktop: {
        display: 'none',
        [theme.breakpoints.up('md')]: {
            display: 'flex',
        },
    },
    sectionMobile: {
        display: 'flex',
        [theme.breakpoints.up('md')]: {
            display: 'none',
        },
    },
});


/*
<div className={classes.search}>
                            <div className={classes.searchIcon}>
                                <SearchIcon />
                            </div>
                            <InputBase
                                placeholder="Search…"
                                classes={{
                                    root: classes.inputRoot,
                                    input: classes.inputInput,
                                }}
                            />
                        </div>
*/

export default withStyles(styles)(NavBar);