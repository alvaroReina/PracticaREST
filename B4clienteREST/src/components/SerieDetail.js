import React, { Component } from 'react'
import Axios from 'axios'
import { SERIES } from '../services/cte'
import { Redirect } from 'react-router'
import SerieEdit from './SerieEdit'
import { Route, Link } from 'react-router-dom'
import { Button, Typography, Grid } from '@material-ui/core';
import SketchesList from './SketchesList'
import SketchNew from "./SketchNew";

import { isAllowed } from '../utils/validator';

export default class SerieDetail extends Component {

    constructor(props) {
        super(props)
        this.state = {
            serie: null,
            loading: true,
            redirect: false
        }
    }

    async componentDidMount() {
        try {
            let resp = await Axios(SERIES + "/" + this.props.match.params.id)


            console.log(resp.data);

            resp = resp.data;

            if (!resp.ok) {
                alert('red');
            }

            let serie = resp.serie.value;

            if (serie) {
                this.setState({ serie, loading: false })
            } else {

            }
        } catch (err) {
            alert('NOT FOUND')
            this.setState({ redirect: true })
        }
    }

    updateSerie = (serie) => {
        this.setState({ serie })
        this.props.updateSerie(serie)
    }


    render() {
        return (
            <div>
                {this.state.redirect && <Redirect to="/" />}
                {this.state.loading && <p>
                    Loading serie {this.props.match.id}...
            </p>}
                {!this.state.loading &&
                    <div>
                        <Route path="/series/:id/edit" render={(props) => <SerieEdit serie={this.state.serie} updateSerie={this.updateSerie} currentUser={this.props.currentUser} {...props} />} />
                        <Route exact path="/series/:id" render={(props) => {
                            return (<Grid>
                                <Typography variant="h2">{this.state.serie.title}</Typography>
                                {(isAllowed(this.props.currentUser, this.state.serie.author.email)) && <Button component={Link} to={'/series/' + this.state.serie.id + '/edit'} color='primary' variant='contained'>Edit</Button>}
                                {(isAllowed(this.props.currentUser, this.state.serie.author.email)) && <Button component={Link} to={'/sketches/new'} color='primary' variant='outlined' >Create Sketch</Button>}
                            </Grid>)
                        }} />
                        <SketchesList serie={this.state.serie} isOwner={(isAllowed(this.props.currentUser, this.state.serie.author.email))} />
                    </div>}
            </div>
        )
    }
}