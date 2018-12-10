import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/Button';
import Popover from '@material-ui/core/Popover';
import MoreVertRounded from '@material-ui/icons/MoreVertRounded';
import {Button} from '@material-ui/core';
import {Link} from 'react-router-dom';

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
        this.edit = this.edit.bind(this);
        this.remove = this.remove.bind(this);
        this.view = this.view.bind(this);
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

    edit() {
        let {serie} = this.props;
        alert(`Editando ${serie.name}`)
        this.close()
    }

    remove() {
        let {serie} = this.props;
        alert(`Eliminando ${serie.name}`)
        this.close()
    }

    view() {
        let {serie} = this.props;
        alert(`Navegando a ${serie.name}`)
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