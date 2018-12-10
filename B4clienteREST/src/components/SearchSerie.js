import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Button, Grid, Typography } from '@material-ui/core';
import GridSeries from './GridSeries';
import { SERIES } from '../services/cte';

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
    },
});
class SearchSerie extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            title: "",
            serieResults: [],
            from: 0,
            to: 10,
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }


     handleSearch = async() => {
        let series = [];
        let {title, from, to} = this.state;

        if (from >= to || from < 0 || to < 0) {
            alert('from must be smaller than to and both greater than zero');
            return;
        }

        try {
            let resp = await Axios.get(`${SERIES}/search?title=${title}&from=${from}&to=${to}`, {headers: {'Authorization': localStorage.getItem("session-token")}});
            resp = resp.data;
            if (resp.ok) {
                series = resp.list.elements;
            } else {
                alert('series err');
            }
        } catch(err) {
           alert(JSON.stringify(err.response.data.error.cause)); 
        }
        
        this.setState({
            serieResults: series,
        });
    }

    handleChange = name => event => {
        let val = event.currentTarget.value;
        this.setState({
            [name]: val
        })
    }

    render() {
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
                            id="search"
                            label="Search parameter"
                            type="search"
                            className={classes.textField}
                            valu={this.state.title}
                            onChange={this.handleChange('title')}
                            margin="normal"
                        />
                        <TextField
                            id="from"
                            type="number"
                            className={classes.smallTextField}
                            margin='normal'
                            label='from'
                            value={this.state.from}
                            onChange={this.handleChange('from')}
                        />
                        <TextField
                            id="to"
                            type="number"
                            className={classes.smallTextField}
                            margin='normal'
                            label='to'
                            value={this.state.to}
                            onChange={this.handleChange('to')}
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
                        {this.state.serieResults.length > 0 ? <GridSeries currentUser={this.props.currentUser} series={this.state.serieResults} /> : <Typography variant='h5' >There are no results</Typography>}
                    </Grid>
                </Grid>

            </div>)
    }
}

SearchSerie.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SearchSerie);