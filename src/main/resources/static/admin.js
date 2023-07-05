const url = 'http://localhost:8080/api/admin';

async function getUsers() {
    const response = await fetch(url, {
        method: "GET",
        headers: { "Accept": "application/json" }
    });
    if (response.ok === true) {
        const users = await response.json();
        getTable(users)
        }
}

function getTable(users) {
    let res = '';
    for (let user of users) {
        res +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td id=${'role' + user.id}>${user.roles.map(r => r.role).join(' ')}</td>
                <td>
                    <button class="btn btn-info" type="button"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                    <button class="btn btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})"
                    > Delete </button></td>
            </tr>`
    }
    document.getElementById('tableBodyAdmin').innerHTML = res;
}
getUsers()

//Редактирование пользователя
function editModal(id) {

    fetch(url + '/' + id,)
        .then(res => {res.json()
            .then(u => {
            document.getElementById('idEdit').value = u.id;
            document.getElementById('nameEdit').value = u.userName;
            document.getElementById('lastNameEdit').value = u.lastName;
            document.getElementById('ageEdit').value = u.age;
            document.getElementById('passEdit').value = u.password;
        })
    });
    let roleSelect = document.getElementById("rolesEdit");
    fetch("http://localhost:8080/api/roles")
        .then(response => response.json())
        .then(roles => {
            roles.forEach((role, i = 0) => {
                let el = document.createElement("option");
                el.value = i+1;
                el.text = role.role.substring(5);
                roleSelect.append(el);
            })
        })
}

function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}

async function editUser() {
    let idValue = document.getElementById("idEdit").value;
    let nameValue = document.getElementById("nameEdit").value;
    let lastNameValue = document.getElementById("lastNameEdit").value;
    let ageValue = document.getElementById('ageEdit').value;
    let passwordValue = document.getElementById("passEdit").value;
    let roleSelect = document.getElementById("rolesEdit");

    let newUserRoles = [];
    for (let i = 0; i < roleSelect.options.length; i++) {
        if (roleSelect.options[i].selected) {
            newUserRoles.push({
                id: roleSelect.options[i].value,
                role: roleSelect.options[i].text
            })
        }
    }
    let user = {
        id: idValue,
        userName: nameValue,
        lastName: lastNameValue,
        age: ageValue,
        password: passwordValue,
        roles: newUserRoles,
    }


    await fetch(url + '/' + user.id, {
        method: "PATCH",
        headers: {
            // 'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    });
    closeModal()
    getUsers()
}
// Удаление пользователя
function deleteModal(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {
            document.getElementById('idDelete').value = u.id;
            document.getElementById('nameDelete').value = u.userName;
            document.getElementById('lastNameDelete').value = u.lastName;
            document.getElementById('ageDelete').value = u.age;
            document.getElementById("deleteRole").value = u.roles.map(r => r.role).join(", ");
        })
    });
}

async function deleteUser() {
    const id = document.getElementById("idDelete").value
    let urlDel = url + "/" + id;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    fetch(urlDel, method).then(() => {
        closeModal()
        getUsers()
    })
}
