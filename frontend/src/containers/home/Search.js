import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import Code from '@material-ui/icons/Code';
import Clear from '@material-ui/icons/Clear';
import Box from '@material-ui/core/Box';
import Tooltip from '@material-ui/core/Tooltip';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { MTableToolbar } from 'material-table';

const classes = makeStyles({
  root: {
    padding: '2px 4px',
    display: 'flex',
    alignItems: 'center',
  },
  input: {
    marginLeft: 8,
    flex: 1,
  },
  iconButton: {
    padding: 10,
  },
  divider: {
    width: 1,
    height: 28,
    margin: 4,
  },
});

export default function Search({ props, filter, find }) {
  const [search, setSearch] = useState(filter);

  return (
    <Box fullWidth lassName={classes.root}>
      <AppBar>
    <Toolbar>
      <Typography variant="h6">SOFTPLAYER</Typography>
      <Tooltip title="Search">
        <IconButton fullWidth className={classes.iconButton} onClick={() => find(search)}>
          <SearchIcon />
        </IconButton>
      </Tooltip>
      <TextField
        style={{flexGrow: 1}} 
        onChange={event => setSearch(event.target.value)}
        inputTypeSearch
        value={search}
        className={classes.input}
        placeholder="Search"
      />
      <Tooltip title="Clear">
        <IconButton className={classes.iconButton} onClick={() => setSearch("")}>
          <Clear />
        </IconButton>
      </Tooltip>
      <Tooltip title="Source">
        <IconButton className={classes.iconButton} onClick={() => window.location.href = 'https://github.com/marcotuliotm/softplayer'}>
          <Code />
        </IconButton>
      </Tooltip>
    </Toolbar>
    </AppBar>
    <MTableToolbar {...props} />
    <MTableToolbar {...props} />
    </Box>
  );
}