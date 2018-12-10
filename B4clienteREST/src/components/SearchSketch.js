import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Button, Grid, Typography } from '@material-ui/core';
import GridSketch from './GridSketch';
import { SKETCHES } from '../services/cte';

import Axios from 'axios';

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
    }, panel: {
        marginLeft: theme.spacing.unit * 2,
    }
});
class SearchSketch extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            from: "",
            to: "",
            sketchResults: [],
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }


    handleSearch = async () => {
        let sketches = [];
        let { from, to } = this.state;

        try {
            from = new Date(from).toISOString().slice(0, 10);
            to = new Date(to).toISOString().slice(0, 10);
        } catch (err) {
            alert('Set valid dates');
            return;
        }

        console.log(from);
        console.log(to);
        try {
            let resp = await Axios.get(`${SKETCHES}/betweendates?from=${from}&to=${to}`, { headers: { 'Authorization': localStorage.getItem("session-token") } });
            console.log(resp.data);
            resp = resp.data;
            if (resp.ok) {
                sketches = resp.list.elements;
            } else {
                alert('sketches err');
            }
        } catch (err) {
            console.log(err.response.data);
            alert(JSON.stringify(err));
        }

        this.setState({
            sketchResults: sketches,
        });
    }

    handleChange = name => event => {
        console.log("handle change", name, event.currentTarget.value);
        let val = event.currentTarget.value;
        this.setState({
            [name]: val
        })
    }

    render() {
        console.log(JSON.stringify(this.state.serieResults));
        let { classes } = this.props;
        return (
            <div>
                <Grid
                    container
                    direction="column"
                    justify="center"
                    alignItems="center"
                >
                    <Grid
                        container
                        direction="row"
                        justify="center"
                        alignItems="center"
                    >
                        <TextField
                            id="fromdate"
                            label="Starting date"
                            type="date"
                            defaultValue={new Date().toLocaleDateString()}
                            className={classes.textField}
                            onChange={this.handleChange('from')}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                        <TextField
                            id="todate"
                            label="Ending date"
                            type="date"
                            defaultValue={new Date().toLocaleDateString()}
                            className={classes.textField}
                            onChange={this.handleChange('to')}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                        <Button variant='contained' onClick={this.handleSearch} color='primary'>Search</Button>
                    </Grid>
                    <Grid
                        container
                        direction="column"
                        justify="flex-start"
                        alignItems="flex-start"
                    >
                        <Typography variant='h3' component='h3'>Results</Typography>
                        {this.state.sketchResults.length > 0 ? <GridSketch currentUser={this.props.currentUser} sketches={this.state.sketchResults} /> : <Typography variant='h5' >There are no results</Typography>}
                    </Grid>
                </Grid>

            </div>)
    }
}

SearchSketch.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchSketch);