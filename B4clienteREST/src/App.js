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
      //TODO coger token en el navegador y comprobarlo
      user: placeholderUser,
      logged: false
    }
    this.login = this.login.bind(this)
    this.logout = this.logout.bind(this)
  }

  //Usuario autenticado a nivel de aplicacion
  async login(gtoken, user) {

    let body = {
      "Gtoken": gtoken
    }

    console.log("With body", body)

    let xml = new XMLHttpRequest();
    xml.open("POST", SIGNIN, true);
    xml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xml.onreadystatechange = (aEvt) => {
      if (xml.readyState === 4) {
        console.log("State 4");
        console.log("StatusCode:", xml.status, xml.statusText)
        console.log("Response Text:",xml.responseText);
        console.log("Headers:", JSON.stringify(xml.getAllResponseHeaders()))
      } else if ( xml.readyState === 3) {
        console.log("State 3");
      } else {
        console.log("Loading", xml.readyState);
      }
    };
    xml.onerror = (err) => {
      console.log("On error", err);
    }

    xml.onabort = (abort) => {
      console.log("Abort", abort)
    }

    xml.send(`Gtoken=${body.Gtoken}`);


    return;


    if (!gtoken) {
      alert("We couldn't sign you in")
      return;
    }

    let sessionToken = undefined;

    try {
      let resp = await Axios.post(SIGNIN, { "Gtoken": gtoken }, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } })
      console.log(resp);

      if (resp.ok) {

        //Expected fields: session-token, user: {email, name, role, id}
        let userDB = resp.user;

        user.email = userDB.email;
        user.name = userDB.name;
        user.role = userDB.role;
        user.id = userDB.id;

        sessionToken = resp["session-token"];

      } else {
        //TODO mostrar al usuario detalles de por qué ha fallado con cause, fields, hint.
        alert(resp.error.cause);
        return;
      }



    } catch (ex) {
      console.log('Signin failed due to', ex);
      return;
    }

    if (!sessionToken) {
      alert('sessionToken not present')
      return;
    }

    localStorage.setItem("session-token", sessionToken);
    this.setState({ logged: true, user, sessionToken })
  }

  logout() {
    localStorage.removeItem("session-token");
    this.setState({ logged: false, user: placeholderUser, sessionToken: undefined })
  }

  render() {
    return (
      <div className="App">
        <NavBar user={this.state.user} logged={this.state.logged} login={this.login} logout={this.logout} changeUser={this.changeUser} />
        <GridSeries series={data} currentUser={this.state.user} />
      </div>
    );
  }
}

export default App;
