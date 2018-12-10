import { withStyles } from '@material-ui/core/styles';
import React, { Component } from 'react';
import Axios from 'axios';
import { SERIES, SKETCHES } from '../services/cte';
import { Paper, TableCell, Table, TableHead, TableRow, TableBody, Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';

class GridSketch extends Component {

    constructor(props) {

        this.state = {
            series: props.series,
            sketcheS: props.sketches
        }

    }
    
    render() {
    const { classes, sketches, currentUser } = this.props;

    return (
        <div>
            {!this.state.loading && <div>
                <Typography align="center" variant="h4">
                    Sketches
        </Typography>
                <Paper >
                    <Table >
                        <TableHead>
                            <TableRow>
                                <TableCell numeric>ID</TableCell>
                                <TableCell >Title</TableCell>
                                <TableCell numeric>Score</TableCell>
                                <TableCell>Date</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.sketches.map(s => {
                                return (
                                    <TableRow key={s.id}>
                                        <TableCell numeric component="th" scope="row">
                                            {s.id}
                                        </TableCell>
                                        <TableCell>{s.title}</TableCell>
                                        <TableCell numeric>{s.score}</TableCell>
                                        <TableCell>{(new Date(s.createdat)).toLocaleDateString("en-GB")}</TableCell>
                                        <TableCell>
                                            <Button component={Link} to={`/serie/${s.idserie.id}`}>View Serie</Button>
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                        </TableBody>
                    </Table>
                </Paper>
            </div>}
        </div>
    )
    }
}

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper
    },
    gridList: {
        width: 'auto',
        height: 'auto'
    },
    subHeader: {
        background: 'rgba(0,0,0,0.75)',
        color: 'white',
        height: '45px',
    },
})

export default withStyles(styles)(GridSketch)