import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/Button';
import Popover from '@material-ui/core/Popover';
import MoreVertRounded from '@material-ui/icons/MoreVertRounded';
import {Button} from '@material-ui/core';
import {Link} from 'react-router-dom';
import Axios from 'axios';
import {SERIES} from '../services/cte';

const styles = theme => ({
    icon: {
        color: 'rgba(255,255,255,0.85)',
        backgroundColor: 'rgba(0,0,0,0.35)'
    },
})

class SeriePopover extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            open: false,
            anchorEl: null,
        }
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.remove = this.remove.bind(this);
        
    }

    open(event) {
        this.setState({
            anchorEl: event.currentTarget,
          });
    }

    close() {
        this.setState({
            anchorEl: null,
        });
    }

    async remove() {
        let {serie} = this.props;

        try {
            let response = await Axios.delete(`${SERIES}/${serie.id}`, {headers: {'Authorization': localStorage.getItem("session-token")}});
            response = response.data;
            if(response.ok) {
                this.props.loadSeries();
            } else {
                alert('We could not remove that serie');
            }
        } catch(err){
            alert('We coudld not remove that serie');
        }
        this.close()
    }

    render() {
        const { classes, isOwner } = this.props;
        const { anchorEl } = this.state;
        let open = Boolean(anchorEl);

        return (
            <div>
                <IconButton
                    variant='text'
                    aria-owns={open ? 'simple-popper' : undefined}
                    aria-haspopup="true"
                    onClick={this.open}
                    className={classes.icon}>
                    <MoreVertRounded />
                </IconButton>
                <Popover
                    id="simple-popper"
                    open={open}
                    anchorEl={anchorEl}
                    onClose={this.close}
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'center',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'center',
                    }}
                >
                    <Button component={Link} to={'/series/' + this.props.serie.id} color='primary' variant='text'>
                        View
                    </Button>
                    {isOwner && <Button component={Link} to={'/series/' + this.props.serie.id + '/edit'} color='default' variant='text'>Edit</Button>}
                    {isOwner && <Button onClick={this.remove} color='secondary' variant='text'>Remove</Button>}
                </Popover>
            </div>
        )
    }
}

export default withStyles(styles)(SeriePopover);