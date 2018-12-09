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

class SerieNew extends Component {
  render() {
    return (
      <div>
        
      </div>
    )
  }
}

export default withStyles(styles)(SerieNew);