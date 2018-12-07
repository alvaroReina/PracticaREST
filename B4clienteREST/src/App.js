import React, { Component } from 'react';
import GridSeries from './components/GridSeries';
import NavBar from './components/Navbar';
import Axios from 'axios';
import { SIGNIN } from './services/cte'

const placeholderUser = {
  name: "Not logged",
  email: "",
  imageUrl: "http://www.buckinghamandcompany.com.au/wp-content/uploads/2016/03/profile-placeholder-350x350.png"
}

const data = [
  { id: '001', name: 'Doraemon', author: 'Luke international', img: 'https://estaticos.elperiodico.com/resources/jpg/8/0/1481901283808.jpg', score: 5 },
  { id: '002', name: 'SpiderMan', author: 'Marvel', img: 'https://i.blogs.es/26b566/sps4/450_1000.png', score: 6 },
  { id: '003', name: 'Scooby Doo', author: 'Warner Brothers', img: 'https://static.tvtropes.org/pmwiki/pub/images/new_scoow_doow_7704.png', score: 7 },
  { id: '004', name: 'Soldado de invierno', author: 'Marvel', img: 'https://cdn.normacomics.com/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/i/m/image_gallery_1__5.jpg', score: 8 },
  { id: '005', name: 'Shizuka', author: 'Luke international', img: 'https://vignette.wikia.nocookie.net/doblaje/images/3/3e/Shizuka.png/revision/latest?cb=20130707175241&path-prefix=es', score: 9 },
  { id: '006', name: 'Pepe the froggo', author: 'Fido inc', img: 'https://vignette.wikia.nocookie.net/universe-of-smash-bros-lawl/images/5/57/Pepe.png/revision/latest?cb=20160317200950', score: 10 },
  { id: '007', name: 'Doraemon', author: 'Luke international', img: 'https://estaticos.elperiodico.com/resources/jpg/8/0/1481901283808.jpg', score: 5 },
  { id: '107', name: 'Slim Shady', author: 'Dr. DRE', img: 'https://i.kinja-img.com/gawker-media/image/upload/s--0UmfJNiN--/c_scale,f_auto,fl_progressive,q_80,w_800/hidqn9he0qhihiyufu1p.png', score: 5 },
  { id: '008', name: 'SpiderMan', author: 'Marvel', img: 'https://i.blogs.es/26b566/sps4/450_1000.png', score: 6 },
  { id: '009', name: 'Scooby Doo', author: 'Warner Brothers', img: 'https://static.tvtropes.org/pmwiki/pub/images/new_scoow_doow_7704.png', score: 7 },
  { id: '010', name: 'Soldado de invierno', author: 'Marvel', img: 'https://cdn.normacomics.com/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/i/m/image_gallery_1__5.jpg', score: 8 },
  { id: '011', name: 'Shizuka', author: 'Luke international', img: 'https://vignette.wikia.nocookie.net/doblaje/images/3/3e/Shizuka.png/revision/latest?cb=20130707175241&path-prefix=es', score: 9 },
  { id: '108', name: 'DREDRE', author: 'Dr. DRE', img: 'https://img.discogs.com/YdfpLimToPq2c55b0y0MqnfkKNQ=/fit-in/600x596/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-300076-1480161960-6406.jpeg.jpg', score: 5 },
  { id: '012', name: 'Pepe the froggo', author: 'Fido inc', img: 'https://vignette.wikia.nocookie.net/universe-of-smash-bros-lawl/images/5/57/Pepe.png/revision/latest?cb=20160317200950', score: 10 },
  { id: '013', name: 'Doraemon', author: 'Luke international', img: 'https://estaticos.elperiodico.com/resources/jpg/8/0/1481901283808.jpg', score: 5 },
  { id: '014', name: 'SpiderMan', author: 'Marvel', img: 'https://i.blogs.es/26b566/sps4/450_1000.png', score: 6 },
  { id: '015', name: 'Scooby Doo', author: 'Warner Brothers', img: 'https://static.tvtropes.org/pmwiki/pub/images/new_scoow_doow_7704.png', score: 7 },
  { id: '016', name: 'Soldado de invierno', author: 'Marvel', img: 'https://cdn.normacomics.com/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/i/m/image_gallery_1__5.jpg', score: 8 },
  { id: '017', name: 'Shizuka', author: 'Luke international', img: 'https://vignette.wikia.nocookie.net/doblaje/images/3/3e/Shizuka.png/revision/latest?cb=20130707175241&path-prefix=es', score: 9 },
  { id: '018', name: 'Pepe the froggo', author: 'Fido inc', img: 'https://vignette.wikia.nocookie.net/universe-of-smash-bros-lawl/images/5/57/Pepe.png/revision/latest?cb=20160317200950', score: 10 },

]

class App extends Component {

  constructor(props) {
    super(props)
    this.state = {
      user: placeholderUser,
      logged: false,
      sessionToken: undefined
    }
    this.login = this.login.bind(this)
    this.logout = this.logout.bind(this)   
  }

  componentDidMount(){
    let sessionToken = localStorage.getItem("session-token");
    let storedUser   = localStorage.getItem("userinfo");
    if (sessionToken && storedUser){
      //Try to recover info
      this.setState({logged: true, user: JSON.parse(storedUser), sessionToken})
    } else {
      localStorage.removeItem("session-token");
      localStorage.removeItem("userinfo");
    }
  }

  //Usuario autenticado a nivel de aplicacion
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

  render() {
    return (
      <div className="App">
        <NavBar user={this.state.user} logged={this.state.logged} login={this.login} logout={this.logout} />
        <GridSeries series={data} currentUser={this.state.user} />
      </div>
    );
  }
}

export default App;
