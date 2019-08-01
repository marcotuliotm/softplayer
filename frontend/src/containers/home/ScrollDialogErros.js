import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

export default function ScrollDialog({errors=[], cancel}) {

  function handleClose() {
    cancel();
  }

  return (
    <div>
      <Dialog
        open={errors.length}
        onClose={handleClose}
        scroll={'paper'}
        aria-labelledby="scroll-dialog-title"
      >
        <DialogTitle id="scroll-dialog-title">Invalid Fields</DialogTitle>
        <DialogContent dividers={true}>
          <DialogContentText>
            {errors.map(erro => (<DialogContentText> {erro.propertyPath.toUpperCase()} : {erro.message}</DialogContentText>))}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}