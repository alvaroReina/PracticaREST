
import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Button, Grid, Typography } from '@material-ui/core';
import GridSeries from './GridSeries';
import { SERIES, SKETCHES } from '../services/cte';
import Axios from 'axios';

const BASE_REQ = `https://comicvine.gamespot.com/api/series_list/?api_key=2939460b79166aad1121ca55eb7d41803186c9c6&format=json&filter=name:`;


class ComicExternal extends React.Component {

    constructor(props) {
        super(props);
        this.state = {name: "bat"};
        this.submit = this.submit.bind(this);
        this.onChange = this.onChange.bind(this);
    }   

    onChange = name => event => {
        this.setState({
            [name] : event.currentTarget.value
        });
    }

    submit = async () => {
        try {
        let resp = await Axios.get(`${BASE_REQ}spi`, {headers: {'Content-Type': 'application/json'}})
        console.log(resp);
        } catch(err) {
            console.log(err);
        }
        
    }

    render() {
        const {classes} = this.props;
        return (
            <div>
                <Grid>
                <TextField
                            id="search"
                            label="Search parameter"
                            type="search"
                            className={classes.textField}
                            value={this.state.name}
                            onChange={this.onChange('name')}
                            margin="normal"
                        />
                        <Button variant='contained' color='primary' onClick={this.submit}>Search</Button>
                </Grid>
            </div>
        )
    }
}


const styles = theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: 400,
    },
    smallTextField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: 75,
    },
    dense: {
        marginTop: 19,
    },
    menu: {
        width: 200,
    },
});

export default withStyles(styles) (ComicExternal);