import React, { Component } from 'react'
import Axios from 'axios'
import { SERIES } from '../services/cte'
import { Redirect } from 'react-router'

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
        let serie = await Axios(SERIES + "/" + this.props.match.match.params.id)
        if(serie) {
            console.log(serie)
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
                Loading serie {this.props.match.match.id}...
            </p>}
            {!this.state.loading && <p>{this.state.serie.title}</p>}
        </div>
        )
  }
}