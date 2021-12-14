import React, { useState, useEffect, useMemo, useRef } from "react";
import Pagination from "@material-ui/lab/Pagination";
import CustomerDataService from "../services/CustumerService";
import { useTable } from "react-table";

const CustomersList = (props) => {
  const [customers, setCustomers] = useState([]);
  const [customersFiltered, setCustomersFiltered] = useState([]);
  const [msgErro, setMsgErro] = useState([]);
  const [searchName, setSearchName] = useState("");
  const customersRef = useRef();

  const [page, setPage] = useState(1);
  const [count, setCount] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalItens, setTotalItens] = useState(0);

  const pageSizes = [5, 10, 20];

  customersRef.current = customersFiltered;



  const onChangeSearchName = (e) => {
    const searchName = e.target.value;
    setSearchName(searchName);
    handleTextSort(searchName);
  };

  const getStatusName = value => {
    let status = [
      {value:true, label: "Active"},
      {value:false, label: "Inactive"}
    ];
    let total = status.length;
    for(let i = 0; i < total; i++){
      if(status[i].value === value){
        return status[i].label;
      }
    }
  }

  const handleTextSort = (searchName) => {
    let textFilter = searchName.toLowerCase();

    let copyData = [];
    if(textFilter !== undefined && textFilter !== ''){
      copyData = customers.filter(obj => 
        (obj.firstName + " " + obj.lastName).toLowerCase().includes(textFilter) || 
        obj.email.toLowerCase().includes(textFilter) || 
        obj.birthDate.includes(textFilter) || 
        obj.state.toLowerCase().includes(textFilter) || 
        obj.city.toLowerCase().includes(textFilter) || 
        getStatusName(obj.enable).includes(textFilter)
      );
    }else{
      copyData = customers;
    }
    
    setCustomersFiltered(copyData);
  };

  const getData = () => {
    return {
      customer: {
        databaseId: null,
        idCustomer: null,
        firstName: null,
        lastName: null,
        email: null,
        birthDate: null,
        state: null,
        city: null,
        createAt: null,
        updateAt: null,
        enable: true,
        removed: false,
        removedAt: null
      },
      searchName: searchName,
      // count: count,
      page: page,
      pageSize: pageSize
      // totalItens: totalItens
    }
  };

  const retrieveCustomers = (data) => {
    if(data === undefined || data === null){
      data = getData();
    }

    CustomerDataService.getAll(data)
      .then((response) => {
        let msg = [];
        if(response){
          const { customers, count, totalItens, error } = response;
          
          setCustomers(customers);
          setCustomersFiltered(customers);
          setCount(count);
          setTotalItens(totalItens);

          if(error.length > 0){
            for(let i = 0; i < error.length; i++){
              msg.push(
                <div key={"msg-"+i} className="col-md-12 item">
                  {(i+1) + ". " + error[i]}
                </div>
              );
            }
            setMsgErro(msg);
          }else{
            setMsgErro([]);
          }
        }else{
          msg.push(
            <div key={"msg-not-found"} className="col-md-12 item">
              No data found!
            </div>
          );
          setMsgErro(msg);
        }
      })
      .catch((e) => {
      });
  };

  const disableAllCustomers = () => {
    CustomerDataService.disableAll()
      .then((response) => {
            
        setPage(1);
        let data = getData();
        // data.totalItens = 0;
        retrieveCustomers(data);
      })
      .catch((e) => {
      });
  };

  const activateAllCustomers = () => {
    CustomerDataService.activateAll()
      .then((response) => {
            
        setPage(1);
        let data = getData();
        // data.totalItens = 0;
        retrieveCustomers(data);
      })
      .catch((e) => {
      });
  };

  const findByName = () => {
    setPage(1);
    let data = getData();
    // data.totalItens = 0;
    retrieveCustomers(data);
  };

  const openCustomer = (rowIndex) => {
    const id = customersRef.current[rowIndex].idCustomer;

    props.history.push("/customers/" + id);
  };

  const deleteCustomer = (rowIndex) => {
    const id = customersRef.current[rowIndex].idCustomer;

    CustomerDataService.remove(id)
      .then((response) => {
        // props.history.push("/customers");

        let newCustomers = [...customersRef.current];
        newCustomers[rowIndex].removed = true;

        newCustomers = newCustomers.filter(obj => obj.removed == false );
        setCustomersFiltered(newCustomers);
        
        let customers = customers.filter(obj => obj.removed == false );
        setCustomers(customers);
      })
      .catch((e) => {
      });
  };

  const handlePageChange = (event, value) => {
    setPage(value);
  };

  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value);
    setPage(1);
  };

  const columns = useMemo(
    () => [
      {
        Header: "First Name",
        accessor: "firstName",
      },
      {
        Header: "Last Name",
        accessor: "lastName",
      },
      {
        Header: "Age",
        accessor: "age",
      },
      {
        Header: "E-mail",
        accessor: "email",
      },
      {
        Header: "State",
        accessor: "state",
      },
      {
        Header: "City",
        accessor: "city",
      },
      {
        Header: "Status",
        accessor: "enable",
        Cell: (props) => {
          return props.value ? "Active" : "Inactive";
        },
      },
      {
        Header: "Update At",
        accessor: "createAt",
      },
      {
        Header: "Actions",
        accessor: "actions",
        Cell: (props) => {
          const rowIdx = props.row.id;
          return (
            <div>
              <span onClick={() => openCustomer(rowIdx)}>
                <i className="far fa-edit action mr-2"></i>
              </span>

              <span onClick={() => deleteCustomer(rowIdx)}>
                <i className="fas fa-trash action"></i>
              </span>
            </div>
          );
        },
      },
    ],
    []
  );

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
  } = useTable({
    columns,
    data: customersFiltered,
  });

  useEffect(retrieveCustomers, [page, pageSize]);


  return (
    <div className="list row">
      <div className="col-md-8">
        <div className="input-group mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Search"
            value={searchName}
            onChange={onChangeSearchName}
          />
          <div className="input-group-append">
            <button
              className="btn btn-outline-secondary"
              type="button"
              onClick={findByName}
            >
              Search
            </button>
          </div>
        </div>
      </div>

      <div className="col-md-12 list">
        <div className="mt-3">
          {"Items per Page: "}
          <select onChange={handlePageSizeChange} value={pageSize}>
            {pageSizes.map((size) => (
              <option key={size} value={size}>
                {size}
              </option>
            ))}
          </select>

          <Pagination
            className="my-3"
            count={count}
            page={page}
            siblingCount={1}
            boundaryCount={1}
            variant="outlined"
            shape="rounded"
            onChange={handlePageChange}
          />
        </div>

        <table
          className="table table-striped table-bordered"
          {...getTableProps()}
        >
          <thead>
            {headerGroups.map((headerGroup) => (
              <tr {...headerGroup.getHeaderGroupProps()}>
                {headerGroup.headers.map((column) => (
                  <th {...column.getHeaderProps()}>
                    {column.render("Header")}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody {...getTableBodyProps()}>
            {rows.map((row, i) => {
              prepareRow(row);
              return (
                <tr {...row.getRowProps()}>
                  {row.cells.map((cell) => {
                    return (
                      <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                    );
                  })}
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      <div className="col-md-8">
        <button className="btn btn-sm btn-success btn-space" onClick={activateAllCustomers}>
          Enable All
        </button>
        <button className="btn btn-sm btn-danger" onClick={disableAllCustomers}>
          Disable All
        </button>
      </div>
      <div className="col-md-12 msg">
        {msgErro.length > 0 ? "Warnings:" : ""}
        {msgErro}
      </div>
    </div>
  );
};

export default CustomersList;
