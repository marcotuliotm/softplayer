import React from 'react'
import { push } from 'connected-react-router'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import Container from '@material-ui/core/Container';
import CssBaseline from '@material-ui/core/CssBaseline';
import MaterialTable from 'material-table';
import { StylesProvider, createGenerateClassName } from '@material-ui/styles';
import {
  find,
  loadAll,
  create,
  remove,
  edit,
  cancel,
} from '../../modules/person'
import ScrollDialog from './ScrollDialogErros';
import Search from './Search';

const columns = [
  { title: 'Name', field: 'name' },
  { title: 'CPF', field: 'cpf' },
  { title: 'Email', field: 'email' },
  { title: 'Gender', field: 'gender', lookup: { MALE: 'Male', FEMALE: 'Female' }, },
  { title: 'Birth', field: 'birth', type: 'date' },
  { title: 'From', field: 'placeOfBirth' },
  { title: 'Nationality', field: 'nationality' },
];

const generateClassName = createGenerateClassName({
  productionPrefix: 'mt',
  seed: 'mt'
});

const options = {
  actionsColumnIndex: -1,
  search: false,
  toolbarButtonAlignment: 'left',
  pageSize: 10,
  showTitle: false,
}

class Home extends React.Component {

  componentDidMount() {
    this.props.loadAll();
  };

  render() {
    const { persons, create, remove, edit, loading, errors, cancel, find, filter } = this.props;

    return (
      <StylesProvider generateClassName={generateClassName}>
        <Container display='flex' component="main" maxWidth="lg">
          <CssBaseline />
          <ScrollDialog errors={errors} cancel={cancel} />
          <MaterialTable
            components={{ Toolbar: props => (<Search props={props} find={find} filter={filter} />)}}
            columns={columns}
            isLoading={loading}
            data={persons}
            options={options}
            localization={{
              header: {
                  actions: ''
              },
          }}
            editable={{
              onRowAdd: newData => new Promise((resolve, reject) => {
                create(newData);
                resolve();
              }),
              onRowUpdate: (newData, oldData) => new Promise((resolve, reject) => {
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
      </StylesProvider>
    );
  }
}

const mapStateToProps = ({ person }) => ({
  persons: person.data,
  loading: person.loading,
  errors: person.error,
  filter: person.filter,
})

const mapDispatchToProps = dispatch =>
  bindActionCreators(
    {
      find,
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
