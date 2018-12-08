import React, { Component } from 'react';
import GridSeries from './components/GridSeries';
import NavBar from './components/Navbar';
import SerieDetail from './components/SerieDetail'
import Axios from 'axios';
import { SIGNIN, SERIES } from './services/cte'
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";


const placeholderUser = {
  name: "Not logged",
  email: "",
  imageUrl: "http://www.buckinghamandcompany.com.au/wp-content/uploads/2016/03/profile-placeholder-350x350.png"
}

class App extends Component {

  constructor(props) {
    super(props)
    this.state = {
      user: placeholderUser,
      logged: false,
      sessionToken: undefined,
      series: []
    }
    this.login = this.login.bind(this)
    this.logout = this.logout.bind(this)   
  }

  async componentDidMount(){
    let sessionToken = localStorage.getItem("session-token");
    let storedUser   = localStorage.getItem("userinfo");
    if (sessionToken && storedUser){
      //Try to recover info
      this.setState({logged: true, user: JSON.parse(storedUser), sessionToken})
    } else {
      localStorage.removeItem("session-token");
      localStorage.removeItem("userinfo");
    }
    let response = await Axios.get(SERIES)
    this.setState({series: response.data})
  }

  async login(gtoken, user) {
    
    if (!gtoken) {
      alert("We couldn't sign you in")
      return;
    }

    let sessionToken = undefined;

    let params = new URLSearchParams();
    params.append('Gtoken', gtoken);

    try {

      let resp = await Axios.post(SIGNIN, params, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } })

      let data = resp.data;
      if (data.ok) {
        //Expected fields: session-token, user: {email, fullname, userrole, id}
        let userDB = data.user.value;
        user.email = userDB.email;
        user.name = userDB.fullname;
        user.role = userDB.userrole;
        user.id = userDB.id;

        sessionToken = data["session-token"];

      } else {
        alert(data.error.cause);
        return;
      }



    } catch (ex) {
      alert('Signing not available');
      return;
    }

    if (!sessionToken) {
      alert('sessionToken not present')
      return;
    }

    localStorage.setItem("session-token", sessionToken);
    localStorage.setItem("userinfo", JSON.stringify(user));
    this.setState({ logged: true, user, sessionToken })
  }

  logout() {
    localStorage.removeItem("session-token");
    localStorage.removeItem("userinfo");
    this.setState({ logged: false, user: placeholderUser, sessionToken: undefined })
  }

  removeData = () => {
    this.setState({data: []})
  }

  render() {
    return (
      <div className="App">
       <Router>
         <div>
            <NavBar user={this.state.user} logged={this.state.logged} login={this.login} logout={this.logout} />  
          
            <Switch>
              <Route exact path="/(series|)" render={() => <GridSeries series={this.state.series} currentUser={this.state.user}/>}/>
              <Route path="/series/:id" render={(match) => <SerieDetail currentUser={this.state.user} match={match}/>}/>
            </Switch>
          </div>
        </Router>
      </div>
    );
  }
}

export default App;
