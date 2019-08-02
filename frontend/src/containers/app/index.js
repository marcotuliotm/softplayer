import React from 'react'
import {Route} from 'react-router-dom';
import Home from '../home'

const App = () => (
  <div>
    <main>
      <Route exact path="/" component={Home} />
      <Route path='/source' component={() => {
        window.location.href = 'https://github.com/marcotuliotm/softplayer';
        return null;
      }} />
    </main>
  </div>
)

export default App
