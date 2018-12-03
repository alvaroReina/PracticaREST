import React, { Component } from 'react';
import GridSeries from './components/GridSeries';
import NavBar from './components/Navbar';

const currentUser = {
  FirstName: "Stan",
  LastName: "Lee",
  email: "Marvel",
  picture: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvIHJk5Ir2ET21cuSYdj4Gv9c4Z1nHVwgvci5XT6hMA_7Ai4GU3A"
}

const data = [
  {id: '001',name: 'Doraemon', author: 'Luke international', img:'https://estaticos.elperiodico.com/resources/jpg/8/0/1481901283808.jpg', score:5 },
  {id: '002',name: 'SpiderMan', author: 'Marvel', img: 'https://i.blogs.es/26b566/sps4/450_1000.png', score:6 },
  {id: '003',name: 'Scooby Doo', author: 'Warner Brothers', img: 'https://static.tvtropes.org/pmwiki/pub/images/new_scoow_doow_7704.png' , score:7 },
  {id: '004',name: 'Soldado de invierno', author: 'Marvel', img: 'https://cdn.normacomics.com/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/i/m/image_gallery_1__5.jpg', score:8 },
  {id: '005',name: 'Shizuka', author:'Luke international', img: 'https://vignette.wikia.nocookie.net/doblaje/images/3/3e/Shizuka.png/revision/latest?cb=20130707175241&path-prefix=es', score:9 },
  {id: '006',name: 'Pepe the froggo', author: 'Fido inc', img: 'https://vignette.wikia.nocookie.net/universe-of-smash-bros-lawl/images/5/57/Pepe.png/revision/latest?cb=20160317200950', score:10 },
  {id: '007',name: 'Doraemon', author: 'Luke international', img:'https://estaticos.elperiodico.com/resources/jpg/8/0/1481901283808.jpg', score:5 },
  {id: '107',name: 'Slim Shady', author: 'Dr. DRE', img:'https://i.kinja-img.com/gawker-media/image/upload/s--0UmfJNiN--/c_scale,f_auto,fl_progressive,q_80,w_800/hidqn9he0qhihiyufu1p.png', score:5 },
  {id: '008',name: 'SpiderMan', author: 'Marvel', img: 'https://i.blogs.es/26b566/sps4/450_1000.png', score:6 },
  {id: '009',name: 'Scooby Doo', author: 'Warner Brothers', img: 'https://static.tvtropes.org/pmwiki/pub/images/new_scoow_doow_7704.png' , score:7 },
  {id: '010',name: 'Soldado de invierno', author: 'Marvel', img: 'https://cdn.normacomics.com/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/i/m/image_gallery_1__5.jpg', score:8 },
  {id: '011',name: 'Shizuka', author:'Luke international', img: 'https://vignette.wikia.nocookie.net/doblaje/images/3/3e/Shizuka.png/revision/latest?cb=20130707175241&path-prefix=es', score:9 },
  {id: '108',name: 'DREDRE', author: 'Dr. DRE', img:'https://img.discogs.com/YdfpLimToPq2c55b0y0MqnfkKNQ=/fit-in/600x596/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-300076-1480161960-6406.jpeg.jpg', score:5 },
  {id: '012',name: 'Pepe the froggo', author: 'Fido inc', img: 'https://vignette.wikia.nocookie.net/universe-of-smash-bros-lawl/images/5/57/Pepe.png/revision/latest?cb=20160317200950', score:10 },
  {id: '013',name: 'Doraemon', author: 'Luke international', img:'https://estaticos.elperiodico.com/resources/jpg/8/0/1481901283808.jpg', score:5 },
  {id: '014',name: 'SpiderMan', author: 'Marvel', img: 'https://i.blogs.es/26b566/sps4/450_1000.png', score:6 },
  {id: '015',name: 'Scooby Doo', author: 'Warner Brothers', img: 'https://static.tvtropes.org/pmwiki/pub/images/new_scoow_doow_7704.png' , score:7 },
  {id: '016',name: 'Soldado de invierno', author: 'Marvel', img: 'https://cdn.normacomics.com/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/i/m/image_gallery_1__5.jpg', score:8 },
  {id: '017',name: 'Shizuka', author:'Luke international', img: 'https://vignette.wikia.nocookie.net/doblaje/images/3/3e/Shizuka.png/revision/latest?cb=20130707175241&path-prefix=es', score:9 },
  {id: '018',name: 'Pepe the froggo', author: 'Fido inc', img: 'https://vignette.wikia.nocookie.net/universe-of-smash-bros-lawl/images/5/57/Pepe.png/revision/latest?cb=20160317200950', score:10 },

]

class App extends Component {
  render() {
    return (
      <div className="App">
        <NavBar user={currentUser}/>
        <GridSeries series={data} currentUser={currentUser}/>
      </div>
    );
  }
}

export default App;
