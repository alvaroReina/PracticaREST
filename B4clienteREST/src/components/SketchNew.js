import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/core/styles';
import {Button} from '@material-ui/core';
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router'
import Axios from 'axios'
import { SERIES, SKETCHES } from '../services/cte'

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
  

class SketchNew extends Component {
    constructor(props){
        super(props)
        this.state = {
            title: "",
            createdAt: new Date().toLocaleTimeString(),
            score:"",
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
        alert('submit');
    }

    render() {
        let { classes } = this.props
        return (
        <form className={classes.container}>
            {this.state.redirect && <Redirect to={`/series/${this.props.serie.id}`}/>}
            <TextField className={classes.textField} id="title" label="Title" placeholder="Title" value={this.state} onChange={this.handleTitle} margin="normal" />
            <TextField className={classes.textField} id="picture" label="Picture" placeholder="Picture Url"  margin="normal" />
            <TextField className={classes.textField} id="picture" label="score" type="number" placeholder="score" margin="normal" />
        </form>
        )
    }
}

export default withStyles(styles)(SketchNew);