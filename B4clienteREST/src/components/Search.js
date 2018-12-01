import React from 'react';
import { IconButton, InputBase, TextField, Popover } from '@material-ui/core';

import SearchIcon from '@material-ui/icons/Search';
import { withStyles } from '@material-ui/core/styles';
import { fade } from '@material-ui/core/styles/colorManipulator';

class Search extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            filterAnchor: null,
        }
    }

    handleMoreFilters = event => {
        this.setState({
            filterAnchor: event.currentTarget()
        })
    }

    closeFilters() {
        this.setState({
            filterAnchor: null
        })
    }

    render() {

        const { classes } = this.props;
        const { filterAnchor } = this.state;
        const isFilterOpen = Boolean(filterAnchor);

        const additionalFilters = (

            <Popover
                id="simple-popper"
                open={isFilterOpen}
                anchorEl={filterAnchor}
                onClose={this.closeFilters}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'center',
                }}
            ></Popover>
        )

        return (
            <div className={classes.searchForm}>
                <input placeholder='Serie name...' type='text' name='search-name' className={`${classes.mainInput} ${classes.Input}`}/>
                <input placeholder='Author...' type='text' name='search-name' className={ classes.Input }/>
                <IconButton className={classes.searchButton}>
                    <SearchIcon/>
                </IconButton>
            </div>
        )
    }

}

const styles = theme => ({
    searchForm: {
        marginLeft: theme.spacing.unit * 2,
        display: 'block',
        [theme.breakpoints.up('md')]: {
            display: 'inline',
        }
    },
    Input :{
        background: 'white',
        paddingTop: theme.spacing.unit,
        paddingBottom: theme.spacing.unit,
        paddingLeft: theme.spacing.unit * 2,
        paddingRight: theme.spacing.unit,
        border: '2px solid rgba(255,255,255,0.35)',
        borderRadius: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        marginBottom: theme.spacing.unit,
        marginTop: theme.spacing.unit,
        display: 'flex',
        [theme.breakpoints.up('md')]: {
            display: 'inline',
            marginBottom: 0,
            marginTop:0
        }
    },
    lastControl: {
        background: 'white',
    },
    searchButton: {
        backgroundColor: fade(theme.palette.common.white, 0.15),
        '&:hover': {
            backgroundColor: fade(theme.palette.common.white, 0.25),
        },
        color: 'white',
    },
    search: {
        position: 'relative',
        borderRadius: theme.shape.borderRadius,
        backgroundColor: fade(theme.palette.common.white, 0.15),
        '&:hover': {
            backgroundColor: fade(theme.palette.common.white, 0.25),
        },
        marginRight: theme.spacing.unit * 2,
        marginLeft: 0,
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            marginLeft: theme.spacing.unit * 3,
            width: 'auto',
        },
    },
    searchIcon: {
        width: theme.spacing.unit * 9,
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    inputRoot: {
        color: 'inherit',
        width: '100%',
    },

    customInput: {
        paddingTop: theme.spacing.unit,
        paddingRight: theme.spacing.unit,
        paddingBottom: theme.spacing.unit,
        paddingLeft: theme.spacing.unit * 5,
        color: 'inherit',
        borderRadius: '10px',
        transition: theme.transitions.create('width'),
        display: 'inline',
        backgroundColor: fade(theme.palette.common.white, 0.15),
        '&:hover': {
            backgroundColor: fade(theme.palette.common.white, 0.25),
        },
    },

    inputInput: {
        paddingTop: theme.spacing.unit,
        paddingRight: theme.spacing.unit,
        paddingBottom: theme.spacing.unit,
        paddingLeft: theme.spacing.unit * 10,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('md')]: {
            width: 200,
        }
    }
})

/*
<div className={classes.searchIcon}>
                        <SearchIcon />
                    </div>
<InputBase
                        name="search-name"
                        placeholder="Search..."
                        classes={{
                            root: classes.inputRoot,
                            input: classes.inputInput,
                        }}
                    />
                    <InputBase
                        name="search-author"
                        placeholder="Search..."
                        classes={{
                            root: classes.inputRoot,
                            input: classes.inputInput,
                        }}
                    />
*/

export default withStyles(styles)(Search);