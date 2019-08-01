import React from 'react'
import { Route, Link as RouterLink } from 'react-router-dom';
import Link from '@material-ui/core/Link';
import Container from '@material-ui/core/Container';
import Home from '../home'

const App = () => (
  <div>
    <Container component="header" maxWidth="lg">

      <Link component={RouterLink} to="/">Softplayer</Link>
      /
      <Link component={RouterLink} to="/source">Source</Link>

    </Container>

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
