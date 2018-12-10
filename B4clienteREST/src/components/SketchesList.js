import React, { Component } from 'react'
import Axios from 'axios'
import { SERIES, SKETCHES } from '../services/cte'
import { Paper, TableCell, Table, TableHead, TableRow, TableBody, Typography, Button } from '@material-ui/core'
import Popup from "reactjs-popup";
import SketchEdit from './SketchEdit'
import SketchNew from './SketchNew'
import { Route } from 'react-router-dom'

export default class SketchesList extends Component {

    constructor(props) {
        super(props)
        this.state = {
            loading: true,
            sketches: []
        }
    }

    componentDidMount() {
        this.loadSketches()
    }

    loadSketches = async () => {
        let sketches = [];
        try {
            let response = await Axios.get(`${SERIES}/${this.props.serie.id}/sketches`)

            response = response.data;
            if (response.ok) {
                sketches = response.list.elements;
            } else {
                alert(response.error.cause);
            }
        } catch (err) {
            console.log(err)
        }
        this.setState({ sketches, loading: false })
    }

    deleteSketch = async (id) => {
        try {
            let resp = await Axios.delete(`${SKETCHES}/${id}`, { headers: { 'Authorization': localStorage.getItem("session-token") } });
            resp = resp.data;
            if (resp.ok) {
                this.setState({ sketches: this.state.sketches.filter(s => s.id !== id) });
            } else {
                alert('something went wrong');
            }
        } catch (err) {
            alert("Error deleting sketch:", id);
            console.log(err);
        }
    }
    updateSketch = (sketch) => {
        let sketches = this.state.sketches
        let i = sketches.findIndex((s) => s.id === sketch.id)
        if(i >= 0) {
          sketches[i] = sketch
        }
        this.setState({sketches})
      }

    render() {
        return (
            <div>
                <Route path="/series/:id/sketches/new" render={(props) => <SketchNew loadSketches={this.loadSketches} logged={this.props.isOwner} {...props}/>}/>
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
                                            {this.props.isOwner && <TableCell>

                                            </TableCell>}
                                            {this.props.isOwner && <TableCell>                                                
                                                <Popup modal trigger={<Button variant="contained" color="primary">EDIT</Button>}>{close => <SketchEdit updateSketch={this.updateSketch} serieId={this.props.serie.id} sketch={s} close={close}/>}</Popup>
                                                <Button variant='contained' color='secondary' onClick={() => { this.deleteSketch(s.id) }}>DELETE</Button>
                                            </TableCell>}
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