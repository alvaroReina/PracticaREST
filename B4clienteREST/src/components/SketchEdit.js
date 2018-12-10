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
  
  class SketchEdit extends Component {
  
    constructor(props) {
      super(props)
      this.state = {
        title: this.props.sketch.title,
        createdat: new Date(this.props.sketch.createdat).toISOString().slice(0, 10),
        score: this.props.sketch.score,
        redirect: false,
      }
  
      this.handleSubmit = this.handleSubmit.bind(this);
      this.handleChange = this.handleChange.bind(this);
      this.validate = this.validate.bind(this);
    }
  
    componentDidMount() {
    }
  
    validate = () => {
      let {title, score} = this.state;
  
      let okTitle = title && title.trim().length > 0;
      let okScore = !isNaN(Number.parseFloat(score));
  
      return (okTitle && okScore);
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
        let sketch = {id: this.props.sketch.id, title,createdat: new Date(createdat), score, idserie: {id: this.props.serieId}}
        let resp = await Axios.put(`${SKETCHES}/${this.props.sketch.id}`, sketch , {headers: {'Authorization': localStorage.getItem("session-token")}});
        resp = resp.data;
        if (resp.ok) {
          this.props.updateSketch(sketch)
          this.setState({redirect: true})
        } else {
          this.notify(`Oops: ${JSON.stringify(resp.error)}`)
        }
      } catch(err) {
        console.log(err);
        this.notify("Something went wrong")
      }
      this.props.close()
    }
  
    notify = (msg) => {
      alert(msg);
    }
  
    render() {
      const { classes } = this.props;
      let valid = this.validate();
      return (
        <div>
          <form className={classes.container} noValidate autoComplete="off">
            <Grid container direction="row" justify="center">
              <Typography variant="h3" className={classes.middleElem}>Update sketch</Typography></Grid>
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
                value={this.state.title}
                onChange={this.handleChange('title')}
                onBlur={this.validate}
                margin="normal"
              />
              <TextField
                id="score"
                label="Score"
                className={classes.textField}
                type="number"
                value={this.state.score}
                onChange={this.handleChange('score')}
                onBlur={this.validate}
                margin="normal"
              />
              <TextField
                id="createdat"
                label="Created at"
                type="date"
                className={classes.textField}
                format="DD/MM/YYYY"
                value={this.state.createdat}
                onChange={this.handleChange('createdat')}
                onBlur={this.validate}
                InputLabelProps={{
                  shrink: true,
                }}
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
                  Update
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

export default withStyles(styles)(SketchEdit);