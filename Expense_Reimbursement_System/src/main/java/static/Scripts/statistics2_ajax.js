
function getPendingAvg(){
    let url = 'http://localhost:5555/reimbursement/getPendingAvg';

    let xhr = new XMLHttpRequest(); //readyState is 0

    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            let stats = JSON.parse(xhr.response);
           
            let stat_container = document.getElementById('stat_container');
            let new_list = document.createElement('ol');
            let new_list_item = document.createElement('li');
            new_list_item.textContent = "Average Pending Reimbursements: $" + stats;
            new_list.appendChild(new_list_item);
            
            stat_container.appendChild(new_list);
        }
    }

    xhr.open('GET', url); //readyState is 1

    xhr.send(); //readyState is 2
}


window.onload = function(){
    this.getPendingAvg();
}