import React, { Component } from 'react'
import Axios from 'axios'
import { SERIES } from '../services/cte'
import { Redirect } from 'react-router'
import SerieEdit from './SerieEdit'
import { Route, Link } from 'react-router-dom'
import {Button, Typography} from '@material-ui/core';
import SketchesList from './SketchesList'

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
        let serie = await Axios(SERIES + "/" + this.props.match.params.id)
        if(serie.data) {
            this.setState({serie: serie.data, loading: false})
        } else {
            this.setState({redirect: true})
        }
    }

    updateSerie = (serie) => {
        this.setState({serie})
        this.props.updateSerie(serie)
    }

    render() {
        return (
        <div>
            {this.state.redirect && <Redirect to="/"/>}
            {this.state.loading && <p>
                Loading serie {this.props.match.id}...
            </p>}
            {!this.state.loading && 
                <div>
                    <Route path="/series/:id/edit" render={(props) => <SerieEdit serie={this.state.serie} updateSerie={this.updateSerie} currentUser={this.props.currentUser} {...props}/> }/>
                    <Route exact path="/series/:id" render={(props) => {return (<div>
                        <Typography variant="h2">{this.state.serie.title}</Typography>
                    {this.props.currentUser.email === this.state.serie.author.email && <Button component={Link} to={'/series/' + this.state.serie.id + '/edit'} color='primary' variant='contained'>Edit</Button>}
                    </div>)}}/>
                    <SketchesList serie={this.state.serie}/>
                </div>}
        </div>
        )
  }
}