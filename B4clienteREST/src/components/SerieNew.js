import React, { Component } from 'react'
import TextField from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/core/styles';
import { Button, Typography, Grid } from '@material-ui/core';
import { Redirect } from 'react-router'
import Axios from 'axios'
import { SERIES } from '../services/cte'
import { notBlank } from '../utils/validator'

import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';

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

class SerieNew extends Component {

  constructor(props) {
    super(props)
    this.state = {
      title: "",
      picture: "",
      score: "",
      redirect: false,
    }

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.validate = this.validate.bind(this);
  }

  componentDidMount() {

    if (!this.props.currentUser) {
      alert('no author!');
    }
  }

  validate = () => {
    let {title, picture, score} = this.state;

    let okTitle = title && title.trim().length > 0;
    let okPic = picture && picture.trim().length > 0
    let okScore = score && !isNaN(Number.parseFloat(score));

    return (okTitle && okPic && okScore);
  }

  handleChange = name => event => {
    this.setState({
      [name]: event.currentTarget.value,
    })
  }

  handleSubmit = async () => {
    let {currentUser} = this.props;
    let { title, picture, score } = this.state;
    if (!this.validate()) {
      this.notify('verify all the data before submit')
      return;
    }

    try {
      let resp = await Axios.post(SERIES, {title,picture, score, author: {id: currentUser.id}})
      if (resp.ok) {
        this.setState({redirect: true})
      } else {
        this.notify(`Oops: ${JSON.stringify(resp.error)}`)
      }
    } catch(err) {
      console.log(err);
      this.notify("Something went wrong")
    }
  }

  notify = (msg) => {
    alert(msg);
  }

  render() {
    const { classes, currentUser } = this.props;
    let valid = this.validate();
    return (
      <div>
        {!this.props.logged && <Redirect to="/" />}
        <form className={classes.container} noValidate autoComplete="off">
          <Grid container direction="row" justify="center">
            <Typography variant="h3" className={classes.middleElem}>Create a new Serie</Typography></Grid>
          <Grid
            container
            direction="row"
            justify="center"
            alignItems="center"
            className={classes.middleElem}
          >
            <TextField
              id="serie-title"
              label="Title"
              className={classes.textField}
              onChange={this.handleChange('title')}
              onBlur={this.validate}
              margin="normal"
            />
            <TextField
              id="score"
              label="Score"
              type='number'
              className={classes.textField}
              onChange={this.handleChange('score')}
              onBlur={this.validate}
              margin="normal"
            />
            <TextField
              id="picture"
              label="Picture"
              className={classes.textField}
              onChange={this.handleChange('picture')}
              onBlur={this.validate}
              placeholder="Paste the image url"
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
            <Typography variant="h5">Preview</Typography>
            <Card className={`${classes.card} ${classes.middleElem}`}>
              <CardActionArea>
                <CardMedia
                  className={classes.media}
                  image={notBlank(this.state.picture) ? this.state.picture : "http://localhost:8080/B4servidorREST/assets/img/not-found.jpg"}
                  title="Contemplative Reptile"
                />
                <CardContent>
                  <Typography gutterBottom variant="h4" component="h2">
                    {notBlank(this.state.title) ? this.state.title : "Your title here"}
                  </Typography>
                  <Typography gutterBottom variant="h6">
                    Score: { this.state.score }
                  </Typography>
                  <Typography gutterBottom variant="h6" >
                    Author: {currentUser.name}
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
            { valid && 
              <Button variant='contained' color='primary' onClick={this.handleSubmit} className={classes.grow}>
                I Like it that way
                </Button>}
            {!valid && 
              <Button variant='outlined' color='secondary' className={classes.grow} disabled={!valid}>
                Fill the Serie first
              </Button>}
          </Grid>
        </form>
      </div>
    )
  }
}

export default withStyles(styles)(SerieNew);