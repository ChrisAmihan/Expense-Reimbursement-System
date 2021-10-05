




function getAllEmployees(){
    let url = 'http://localhost:5555/employee/all';

    let xhr = new XMLHttpRequest(); //readyState is 0

    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            let employees = JSON.parse(xhr.response);         
            
            for(let employee of employees){

            let employee_container = document.getElementById('employee_container');
            
            let new_list = document.createElement('ol');
            let new_list_item = document.createElement('li');          
            new_list_item.textContent = 'Employee-Id:'+ employee.employeeId + ', Name: ' + employee.name + ' Type: ' + employee.employeeType;
            new_list.appendChild(new_list_item);
            
            employee_container.appendChild(new_list);
            }
        }
    }

    xhr.open('GET', url); //readyState is 1

    xhr.send(); //readyState is 2
}

window.onload = function(){
    this.getAllEmployees();
}