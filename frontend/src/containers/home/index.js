import React from 'react'
import { push } from 'connected-react-router'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import Container from '@material-ui/core/Container';
import CssBaseline from '@material-ui/core/CssBaseline';
import MaterialTable from 'material-table';
import {
  loadAll,
  create,
  remove,
  edit,
  cancel,
} from '../../modules/person'
import ScrollDialog from './ScrollDialogErros';

class Home extends React.Component {
 
  componentDidMount() {
    this.props.loadAll();
  };

  render() {
    const { persons, create, remove, edit, loading, errors, cancel} = this.props;
   
    return (
      <Container component="main" maxWidth="lg">
        <CssBaseline />
        <ScrollDialog errors={errors} cancel={cancel}/>
        <MaterialTable
          title="SoftPlayer"
          columns={[
            { title: 'Name', field: 'name' },
            { title: 'CPF', field: 'cpf'},
            { title: 'Email', field: 'email'},
            { title: 'Gender', field: 'gender', lookup: { MALE: 'Male', FEMALE: 'Female' }, },
            { title: 'Birth Year', field: 'birth', type: 'date' },
            { title: 'Place Of Birth', field: 'placeOfBirth'},
            { title: 'Nationality', field: 'nationality'},
          ]}
          isLoading = {loading}
          data={persons}
          editable={{
            onRowAdd: newData =>  new Promise((resolve, reject) => {
              create(newData);
              resolve();
            }),
            onRowUpdate: (newData, oldData) =>new Promise((resolve, reject) => {
              edit(newData);
              resolve();
            }),
            onRowDelete: oldData => new Promise((resolve, reject) => {
              remove(oldData);
              resolve();
            })
          }}
        />
      </Container>
    );
  }
}

const mapStateToProps = ({ person }) => ({
  persons: person.data,
  loading: person.loading,
  errors: person.error
})

const mapDispatchToProps = dispatch =>
  bindActionCreators(
    {
      loadAll,
      create,
      remove,
      edit,
      cancel,
      changePage: () => push('/about-us')
    },
    dispatch
  )

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home)
