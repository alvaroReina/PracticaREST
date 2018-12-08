import React, { Component } from 'react'
import Axios from 'axios'
import { SERIES } from '../services/cte'
import { Redirect } from 'react-router'
import SerieEdit from './SerieEdit'
import { Route, Link } from 'react-router-dom'
import {Button} from '@material-ui/core';

export default class SerieDetail extends Component {

    constructor(props) {
        super(props)
        this.state = {
            serie: null,
            loading: true,
            redirect: false,
        }
    }
    
    async componentDidMount() {
        let serie = await Axios(SERIES + "/" + this.props.match.params.id)
        console.log(serie)
        if(serie.data) {
            this.setState({serie: serie.data, loading: false})
        } else {
            this.setState({redirect: true})
        }
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
                    <Route path="/series/:id/edit" render={(props) => <SerieEdit serie={this.state.serie} currentUser={this.props.currentUser} {...props}/> }/>
                    {this.props.currentUser.email === this.state.serie.author.email && <Button component={Link} to={'/series/' + this.state.serie.id + '/edit'} color='default' variant='text'>Edit</Button>}
                    <p>{this.state.serie.title}<br/>Me gustaria poder hacer una request a {SERIES}/{this.props.match.params.id}/sketches</p>
                </div>}
        </div>
        )
  }
}