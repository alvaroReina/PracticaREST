import React, { Component } from 'react'
import Axios from 'axios'
import {SERIES} from '../services/cte'
import {Paper, TableCell, Table, TableHead, TableRow, TableBody, Typography} from '@material-ui/core'

export default class SketchesList extends Component {

    constructor(props) {
        super(props)
        this.state = {
            loading: true,
            sketches: []
        }
    }

    async componentDidMount() {
        let response = await Axios.get(`${SERIES}/${this.props.serie.id}/sketches`)
        console.log(response)
        this.setState({sketches: response.data, loading: false})
    }
        
    render() {
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
