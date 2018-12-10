import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Button, Grid, Typography } from '@material-ui/core';
import GridSeries from './GridSeries';
import GridSketch from './GridSketch';
import { SERIES, SKETCHES } from '../services/cte';
import SearchSerie from './SearchSerie';
import SearchSketch from './SearchSketch';

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
    panel: {
        marginLeft: theme.spacing.unit * 2,
    }
});
class Results extends React.Component {

    constructor(props) {

        super(props);
        this.state = {
            iDate: "",
            eDate: "",
            sParam: "",
            sketchResults: [],
            serieResults: [],
            from: 0,
            to: 100,
        }
    }


    componentDidMount() {

    }


    async handleSearch() {
        let series = [], sketches = [];
        try {
            let resp = await Axios.get(`${SERIES}/search`, { params: { title: this.state.sParam, from: this.state.from, to: this.state.to } });
            console.log(resp.data);
            resp = resp.data;
            if (resp.ok) {

                series = resp.list.elements;
            } else {
                alert('series err');
            }
        } catch (err) {
            alert(JSON.stringify(err));
        }

        try {
            let resp = await Axios.get(`${SKETCHES}/search`, { params: { title: this.state.sParam, from: this.state.from, to: this.state.to } });
            console.log(resp.data);
            resp = resp.data;
            if (resp.ok) {
                series = resp.list.elements;
            } else {
                alert('series err');
            }
        } catch (err) {
            alert(JSON.stringify(err));
        }

        this.setState({
            serieResults: series,
            sketchResults: sketches
        });
    }


    render() {
        let { classes } = this.props;
        return (
            <div className={classes.panel}>
                <Grid
                    spacing={24}
                    container
                    direction="row"
                    justify="center"
                    alignItems="center"
                >
                    <SearchSerie currentUser={this.props.currentUser} />
                    <SearchSketch currentUser={this.props.currentUser} />
                </Grid>
            </div>)
    }
}

Results.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Results);