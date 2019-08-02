import React from 'react'
import { push } from 'connected-react-router'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import Container from '@material-ui/core/Container';
import CssBaseline from '@material-ui/core/CssBaseline';
import MaterialTable from 'material-table';
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
import { columns, options, localization, } from './Styles';



class Home extends React.Component {

  componentDidMount() {
    this.props.loadAll();
  };

  render() {
    const { persons, create, remove, edit, loading, errors, cancel, find, filter } = this.props;

    return (
     
        <Container display='flex' component="main" maxWidth="lg">
          <CssBaseline />
          <ScrollDialog errors={errors} cancel={cancel} />
          <MaterialTable
            components={{ Toolbar: props => (<Search props={props} find={find} filter={filter} />) }}
            columns={columns}
            isLoading={loading}
            data={persons}
            options={options}
            localization={localization}
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
