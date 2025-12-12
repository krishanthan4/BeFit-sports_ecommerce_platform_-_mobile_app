async function updateProfile() {

    const firstName = document.getElementById("fname").value;
    const lastName = document.getElementById("lname").value;
    const mobile = document.getElementById("mobile").value;
    const gender = document.getElementById("gender").value;
    const line1 = document.getElementById("line1").value;
    const line2 = document.getElementById("line2").value;
    const province = document.getElementById("province").value;
    const district = document.getElementById("district").value;
    const city = document.getElementById("city").value;
    const pcode = document.getElementById("pcode").value;
    const image = document.getElementById("userImage").files[0];
    const formData = new FormData();
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("mobile", mobile);
    formData.append("gender", gender);
    formData.append("line1", line1);
    formData.append("line2", line2);
    formData.append("province", province);
    formData.append("district", district);
    formData.append("city", city);
    formData.append("pcode", pcode);
    formData.append("image", image);

    try {
        const profileData = {
            first_name: firstName,
            last_name: lastName,
            mobile,
            gender,
            line1,
            line2,
            province_id: province,
            district_id: district,
            city_id: city,
            postal_code: pcode
        };

        const response = await UserService.updateProfile(profileData);
        if (response.success) {
            document.getElementById("msgToast").classList.remove("hidden");
            document.getElementById("msg").innerHTML = "Profile Updated Successfully!";
            document.getElementById("msgToast").classList.add("border-green-500");
            document.getElementById("msgIcon").classList.add("bg-green-500");
            setTimeout(() => {
                document.getElementById("msgToast").classList.add("hidden");
                window.location.reload();
            }, 2500);
        }
    } catch (error) {
        document.getElementById("msgToast").classList.remove("hidden");
        document.getElementById("msg").innerHTML = error.message;
        setTimeout(() => {
            document.getElementById("msgToast").classList.add("hidden");
        }, 2500);
    }

}



async function loadDistricts() {
    var provinceId = document.getElementById("province").value;
    var districtDropdown = document.getElementById("district");
    districtDropdown.innerHTML = "<option>Loading...</option>";

    try {
        const response = await api.get(`/districts/${provinceId}`);
        if (response.success) {
            districtDropdown.innerHTML = "<option value='0'>Select District</option>";
            response.data.forEach(district => {
                const option = document.createElement("option");
                option.value = district.district_id;
                option.textContent = district.district_name;
                districtDropdown.appendChild(option);
            });
        }
    } catch (error) {
        console.error("Error loading districts:", error);
        districtDropdown.innerHTML = "<option>Error loading districts</option>";
    }
}

function _oldLoadDistricts() {
    var provinceId = document.getElementById("province").value;
    var districtDropdown = document.getElementById("district");
    districtDropdown.innerHTML = "<option>Loading...</option>";


    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/processes/get_districts.php?province_id=" + provinceId, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            districtDropdown.innerHTML = xhr.responseText;
            districtDropdown.disabled = false;
        }
    };
    xhr.send();
}

function loadCities() {
    var districtId = document.getElementById("district").value;
    var cityDropdown = document.getElementById("city");
    cityDropdown.innerHTML = "<option>Loading...</option>";


    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/processes/get_cities.php?district_id=" + districtId, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            cityDropdown.innerHTML = xhr.responseText;
            cityDropdown.disabled = false;
        }
    };
    xhr.send();
}
