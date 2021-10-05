
function getAllReimbursements(){
    let url = 'http://localhost:5555/reimbursement/all';

    let xhr = new XMLHttpRequest(); //readyState is 0

    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            let reimbursements = JSON.parse(xhr.response);         
            
            for(let reimbursement of reimbursements){

            let reimbursement_container = document.getElementById('reimbursement_container');
            
            let new_list = document.createElement('ol');
            let new_list_item = document.createElement('li');  
            //new_list_item.textContent = 'Employee-Id: ' + reimbursement.employee_employeeID + ' Reimbursement-Id:'+ reimbursement.reimbursementId + ' Amount: $' + reimbursement.reimbursementAmt + ' Status: ' + reimbursement.reimbursementStatus + ' Type: ' + reimbursement.Type;        
            new_list_item.textContent = 'Reimbursement-Id:'+ reimbursement.reimbursementId + ' Amount: $' + reimbursement.reimbursementAmt + ' Status: ' + reimbursement.reimbursementStatus;
            new_list.appendChild(new_list_item);
            
            reimbursement_container.appendChild(new_list);
            }
        }
    }

    xhr.open('GET', url); //readyState is 1

    xhr.send(); //readyState is 2
}

window.onload = function(){
    this.getAllReimbursements();
}