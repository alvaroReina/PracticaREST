import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/core/styles';
import {Button} from '@material-ui/core';
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router'
import Axios from 'axios'
import { SERIES } from '../services/cte'

const styles = theme => ({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    textField: {
      marginLeft: theme.spacing.unit,
      marginRight: theme.spacing.unit,
      width: 200,
    },
    dense: {
      marginTop: 19,
    },
    menu: {
      width: 200,
    },
  });
  

class SerieEdit extends Component {
    constructor(props){
        super(props)
        this.state = {
            title: "",
            picture: "",
            redirect: false
        }
    }

    componentDidMount() {
        this.setState({
            title: this.props.serie.title,
            picture: this.props.serie.picture
        })
    }

    handleTitle = (event) => {
        this.setState({title: event.target.value})
    }

    handlePicture = (event) => {
        this.setState({picture: event.target.value})
    }

     handleSubmit = () => {
        let serie = this.props.serie
        serie.title = this.state.title
        serie.picture = this.state.picture
        this.props.updateSerie(serie)
        Axios.put(`${SERIES}/${serie.id}`, serie, {headers: {'Authorization': localStorage.getItem("session-token")}}).then((response) => {
            this.setState({redirect: true})
        })
        

    }

    render() {
        let { classes } = this.props
        return (
        <form className={classes.container}>
            {this.state.redirect && <Redirect to={`/series/${this.props.serie.id}`}/>}
            <TextField className={classes.textField} id="title" label="Title" placeholder="Title" value={this.state.title} onChange={this.handleTitle} margin="normal" />
            <TextField className={classes.textField} id="picture" label="Picture" placeholder="Picture Url" value={this.state.picture} onChange={this.handlePicture} margin="normal" />
            <Button color="primary" onClick={this.handleSubmit} variant="contained">Submit</Button>
            <Button color="secondary" component={Link} to={`/series/${this.props.serie.id}`} variant="contained">Cancel</Button>
        </form>
        )
    }
}

export default withStyles(styles)(SerieEdit);