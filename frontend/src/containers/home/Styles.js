export const columns = [
    { title: 'Name', field: 'name' },
    { title: 'CPF', field: 'cpf' },
    { title: 'Email', field: 'email' },
    { title: 'Gender', field: 'gender', lookup: { MALE: 'Male', FEMALE: 'Female' } },
    { title: 'Birth', field: 'birth', type: 'date' },
    { title: 'From', field: 'placeOfBirth' },
    { title: 'Nationality', field: 'nationality' },
];

export const options = {
    headerStyle: { padding: '10px' },
    search: false,
    toolbarButtonAlignment: 'left',
    pageSize: 10,
    showTitle: false,
    detailPanelColumnAlignment: 'left',
}

export const localization = {
    pagination: {
        labelDisplayedRows: '{from}-{to} of {count}'
    },
    toolbar: {
        nRowsSelected: '{0} row(s) selected'
    },
    header: {
        actions: ''
    },
    body: {
        emptyDataSourceMessage: 'No records to display',
        filterRow: {
            filterTooltip: 'Filter'
        }
    }
}