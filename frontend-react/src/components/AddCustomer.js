import React, { useState } from "react";
import CustumerService from "../services/CustumerService";
import DatePicker from "react-datepicker";

const AddCustomer = () => {
  const initialCustomerState = {
    databaseId: null,
    idCustomer: "",
    firstName: "",
    lastName: "",
    email: "",
    birthDate: "",
    state: "",
    city: "",
    createAt: "",
    updateAt: "",
    enable: true,
    removed: false,
    removedAt: null
  };
  const [customer, setCustomer] = useState(initialCustomerState);
  const [msgErro, setMsgErro] = useState([]);
  const [firstNameErro, setFirstNameErro] = useState(false);
  const [lastNameErro, setLastNameErro] = useState(false);
  const [emailErro, setEmailErro] = useState(false);
  const [birthDateErro, setBirthDateErro] = useState(false);
  const [stateErro, setStateErro] = useState(false);
  const [cityErro, setCityErro] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ];
  
  const range = (start, end) => {
    let years = [];
    for(let i = start; i < end; i++){
      years.push(i);
    }
    return years;
  }

  const getYear = (date) => {
    return date.getFullYear();
  }

  const getMonth = (date) => {
    return date.getMonth();
  }
  const years = range(1900, getYear(new Date())+1);
  


  const handleInputChange = event => {
    const { name, value } = event.target;
    setCustomer({ ...customer, [name]: value });

    if(name == "firstName"){setFirstNameErro(false);}
    if(name == "lastName"){setLastNameErro(false);}
    if(name == "email"){setEmailErro(false);}
    if(name == "state"){setStateErro(false);}
    if(name == "city"){setCityErro(false);}
  };

  const setBirthDate = date => {
    if(date !== null && date !== undefined){
      setBirthDateErro(false);
    }
    setCustomer({ ...customer, ["birthDate"]: date });
  };

  const getBirthDateString = () => {
    return customer.birthDate.getFullYear() + "-"+
            customer.birthDate.getMonth() + "-"+
            customer.birthDate.getDate();
  }

  const getData = () => {
    let valid = true;
    if(!customer.firstName){setFirstNameErro(true); valid = false;}
    if(!customer.lastName){setLastNameErro(true); valid = false;}
    if(!customer.email){setEmailErro(true); valid = false;}
    if(!customer.birthDate){setBirthDateErro(true); valid = false;}
    if(!customer.state){setStateErro(true); valid = false;}
    if(!customer.city){setCityErro(true); valid = false;}
    if(valid){
      return {
        customer: {
          databaseId: null,
          idCustomer: null,
          firstName: customer.firstName,
          lastName: customer.lastName,
          email: customer.email,
          birthDate: getBirthDateString(),
          state: customer.state,
          city: customer.city,
          createAt: null,
          updateAt: null,
          enable: true,
          removed: false,
          removedAt: null
        }
      }
    }
    return null;
  };

  const saveCustomer = () => {
    let request = getData();
    if(request){
      CustumerService.save(getData())
      .then(response => {
        const { customers, error } = response;

        if(error.length > 0){
          let msg = [];
          for(let i = 0; i < error.length; i++){
            msg.push(
              <div key={"msg-"+i} className="col-md-12 item">
                {(i+1) + ". " + error[i]}
              </div>
            );
          }
          setMsgErro(msg);
        }else{
          if(customers.length > 0){
            let obj = customers[0];
            obj.birthDate = new Date(obj.birthDate + " 01:00");
            setCustomer(obj);
            setSubmitted(true);
          }
          setMsgErro([]);
        }
      })
      .catch(e => {
      });
    }
    
  };

  const newCustomer = () => {
    setCustomer(initialCustomerState);
    setSubmitted(false);
  };

  return (
    <div className="container">
      <div className="submit-form">
        {submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={newCustomer}>
              Add Another
            </button>
          </div>
        ) : (
          <>
            <h4>Customer</h4>
            <div className="form__half-left">
              <div className="form-group">
                <label htmlFor="firstName">First Name</label>
                {firstNameErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (firstNameErro ? " error" : "") }
                  id="firstName"
                  name="firstName"
                  value={customer.firstName}
                  onChange={handleInputChange}
                />
              </div>
              <div className="form-group">
                <label htmlFor="email">E-mail</label>
                {emailErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (emailErro ? " error" : "") }
                  id="email"
                  name="email"
                  value={customer.email}
                  onChange={handleInputChange}
                />
              </div>
              <div className="form-group">
                <label htmlFor="state">State</label>
                {stateErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (stateErro ? " error" : "") }
                  id="state"
                  name="state"
                  value={customer.state}
                  onChange={handleInputChange}
                />
              </div>
            </div>
            <div className="form__half-right">
              <div className="form-group">
                <label htmlFor="lastName">Last Name</label>
                {lastNameErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (lastNameErro ? " error" : "") }
                  id="lastName"
                  name="lastName"
                  value={customer.lastName}
                  onChange={handleInputChange}
                />
              </div>
              <div className="form-group">
                <label htmlFor="birthDate">Birth Date</label>
                {birthDateErro ? <span className="span">Required Field!</span> : "" }
                <DatePicker 
                  className={"form-control " + (birthDateErro ? " error" : "") }
                  renderCustomHeader={({
                    date,
                    changeYear,
                    changeMonth,
                    decreaseMonth,
                    increaseMonth,
                    prevMonthButtonDisabled,
                    nextMonthButtonDisabled,
                  }) => (
                    <div
                      style={{
                        margin: 10,
                        width: 240,
                        display: "flex",
                        justifyContent: "center",
                      }}
                    >
                      <button onClick={decreaseMonth} disabled={prevMonthButtonDisabled}>
                        {"<"}
                      </button>
                      <select
                        value={getYear(date)}
                        onChange={({ target: { value } }) => changeYear(value)}
                      >
                        {years.map((option) => (
                          <option key={option} value={option}>
                            {option}
                          </option>
                        ))}
                      </select>

                      <select
                        value={months[getMonth(date)]}
                        onChange={({ target: { value } }) =>
                          changeMonth(months.indexOf(value))
                        }
                      >
                        {months.map((option) => (
                          <option key={option} value={option}>
                            {option}
                          </option>
                        ))}
                      </select>

                      <button onClick={increaseMonth} disabled={nextMonthButtonDisabled}>
                        {">"}
                      </button>
                    </div>
                  )}
                  dateFormat="yyyy/MM/dd"
                  selected={customer.birthDate}
                  onChange={(date) => setBirthDate(date)}
                />
              </div>
              <div className="form-group">
                <label htmlFor="city">City</label>
                {cityErro ? <span className="span">Required Field!</span> : "" }
                <input
                  type="text"
                  className={"form-control" + (cityErro ? " error" : "") }
                  id="city"
                  name="city"
                  value={customer.city}
                  onChange={handleInputChange}
                />
              </div>
            </div>

            <div className="col-md-12 Submit">
              <button onClick={saveCustomer} className="btn btn-success">
                Submit
              </button>
            </div>
            
            <div className="col-md-12 msg">
              {msgErro.length > 0 ? "Erros:" : ""}
              {msgErro}
            </div>
          </>
       )}
      </div>
    </div>
  );
};

export default AddCustomer;
