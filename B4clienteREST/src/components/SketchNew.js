import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/core/styles';
import {Button, Grid, Typography} from '@material-ui/core';
import { Redirect } from 'react-router'
import Axios from 'axios'
import { SKETCHES } from '../services/cte'

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
    card: {
      maxWidth: 600,
      minWidth: 345,
    },
    media: {
      height: 300,
    }, grow: {
      flexGrow: 1,
    },
    middleElem: {
      marginTop: theme.spacing.unit * 2,
      marginBottom: theme.spacing.unit * 2
    }
  });
  
  class SketchNew extends Component {
  
    constructor(props) {
      super(props)
      this.state = {
        title: "",
        redirect: false,
      }
  
      this.handleSubmit = this.handleSubmit.bind(this);
      this.handleChange = this.handleChange.bind(this);
      this.validate = this.validate.bind(this);
    }
  
    componentDidMount() {
    }
  
    validate = () => {
      let {title} = this.state;
  
      let okTitle = title && title.trim().length > 0;
  
      return (okTitle);
    }
  
    handleChange = name => event => {
      this.setState({
        [name]: event.currentTarget.value,
      })
    }
  
    handleSubmit = async () => {
      let { title, createdat, score } = this.state;
      if (!this.validate()) {
        this.notify('verify all the data before submit')
        return;
      }
  
      try {
        let resp = await Axios.post(SKETCHES, {title,createdat, score, idserie: {id: this.props.match.params.id}}, {headers: {'Authorization': localStorage.getItem("session-token")}});
        resp = resp.data;
        if (resp.ok) {
          this.setState({redirect: true})
        } else {
          this.notify(`Oops: ${JSON.stringify(resp.error)}`)
        }
      } catch(err) {
        console.log(err);
        this.notify("Something went wrong")
      }
      this.props.loadSketches()
    }
  
    notify = (msg) => {
      alert(msg);
    }
  
    render() {
      const { classes } = this.props;
      let valid = this.validate();
      return (
        <div>
          {!this.props.logged && <Redirect to="/" />}
          {this.state.redirect && <Redirect to={`/series/${this.props.match.params.id}`}/>}
          <form className={classes.container} noValidate autoComplete="off">
            <Grid container direction="row" justify="center">
              <Typography variant="h3" className={classes.middleElem}>Create a new Sketch</Typography></Grid>
            <Grid
              container
              direction="row"
              justify="center"
              alignItems="center"
              className={classes.middleElem}
            >
              <TextField
                id="title"
                label="Title"
                className={classes.textField}
                onChange={this.handleChange('title')}
                onBlur={this.validate}
                margin="normal"
              />
            </Grid>
  
            <Grid
              container
              direction="column"
              justify="center"
              alignItems="center"
              className={classes.middleElem}
            >
             
              { valid && 
                <Button variant='contained' color='primary' onClick={this.handleSubmit} className={classes.grow}>
                  Create sketch
                  </Button>}
              {!valid && 
                <Button variant='outlined' color='secondary' className={classes.grow} disabled={!valid}>
                  Fill the Sketch first
                </Button>}
            </Grid>
          </form>
        </div>
      )
    }
  }

export default withStyles(styles)(SketchNew);