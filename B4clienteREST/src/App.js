import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import GridSeries from './components/GridSeries';

const data = [
  {id: 5,name: 'Doraemon', author: 'Luke international', img:'https://estaticos.elperiodico.com/resources/jpg/8/0/1481901283808.jpg', score:5 },
  {id: 6,name: 'SpiderMan', author: 'Marvel', img: 'https://i.blogs.es/26b566/sps4/450_1000.png', score:6 },
  {id: 7,name: 'Scooby Doo', author: 'Warner Brothers', img: 'https://static.tvtropes.org/pmwiki/pub/images/new_scoow_doow_7704.png' , score:7 },
  {id: 8,name: 'Soldado de invierno', author: 'Marvel', img: 'https://cdn.normacomics.com/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/i/m/image_gallery_1__5.jpg', score:8 },
  {id: 9,name: 'Shizuka', author:'Luke international', img: 'https://vignette.wikia.nocookie.net/doblaje/images/3/3e/Shizuka.png/revision/latest?cb=20130707175241&path-prefix=es', score:9 },
  {id: 10,name: 'Pepe the froggo', author: 'Fido inc', img: 'https://vignette.wikia.nocookie.net/universe-of-smash-bros-lawl/images/5/57/Pepe.png/revision/latest?cb=20160317200950', score:10 },

]

class App extends Component {
  render() {
    return (
      <div className="App">
        <Button variant="contained" color="primary">
          Hello world
        </Button>
        <GridSeries series={data} currentUser={"Marvel"}/>
      </div>
    );
  }
}

export default App;
