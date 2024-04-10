const userAPI = 'http://localhost:8080/api/user';
const userHeader = document.getElementById("navbar-user");
const userInfo = document.getElementById("user-info");

function getUser() {
    fetch(userAPI)
        .then(res => res.json())
        .then(principal => {
            let roles = "";
            principal.roles.forEach(value => {
                roles += value.name + " "
            })
            userHeader.innerHTML = `<span class="fw-bolder">${principal.email}</span>
                    <span> with roles: </span>
                    <span>${roles}</span>
                   `;
            userInfo.innerHTML = `
                        <th scope="row">${principal.id}</th>
                        <td >${principal.name}</td>
                        <td >${principal.surname}</td>
                        <td >${principal.age}</td>
                        <th >${principal.email}</th>
                        <td>
                            <span>${roles}</span></td>`;
        });
}

getUser();
