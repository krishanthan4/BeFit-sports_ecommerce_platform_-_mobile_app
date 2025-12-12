document.getElementById("searchInput").addEventListener("keypress", searchInputFocus);

function searchInputFocus() {
    if (event.keyCode === 13) {
        let searchInput = document.getElementById("searchInput").value;
        document.getElementById("cancelX").classList.remove("hidden");

        if (searchInput !== "") {
            (async () => {
                try {
                    const response = await api.get('/admin/reviews', { search: searchInput });
                    
                    if (response.success && response.data) {
                        makeHTML(response.data);
                    } else {
                        document.getElementById("subDiv").remove();
                        const subDiv = document.createElement("div");
                        subDiv.classList.add("flex", "flex-col", "gap-3", "mt-2", "overflow-y-scroll", "h-[29rem]");
                        subDiv.id = "subDiv";
                        document.getElementById("mainDiv").append(subDiv);
                    }
                } catch (error) {
                    console.error("Error:", error);
                }
            })();
        } else {
            setDefault();
        }
    }
}


async function setDefault() {
    try {
        const response = await api.get('/admin/reviews', { setDefault: true });
        
        if (response.success && response.data) {
            makeHTML(response.data);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

function filterBy(filter) {

    // document.querySelectorAll("#"+filter).forEach(element => {
    //     element.classList.toggle("bg-gray-300");
    //     element.classList.toggle("hover:bg-gray-400");
    //     element.classList.toggle("bg-gray-500");
    //     element.classList.toggle("hover:bg-gray-600");
    // });

    (async () => {
        try {
            const response = await api.get('/admin/reviews', { filter });
            
            if (response.success && response.data) {
                makeHTML(response.data);
            }
        } catch (error) {
            console.error("Error:", error);
        }
    })();
}


function makeHTML(result) {
    const mainDiv = document.getElementById("mainDiv");
    const oldSubDiv = document.getElementById("subDiv");

    // Remove old subDiv if it exists
    if (oldSubDiv) {
        mainDiv.removeChild(oldSubDiv);
    }

    const subDiv = document.createElement("div");
    subDiv.classList.add("flex", "flex-col", "gap-3", "mt-2", "overflow-y-scroll", "h-[29rem]");
    subDiv.id = "subDiv";
    mainDiv.append(subDiv);

    for (let index = 0; index < Object.keys(result).length; index++) {
        createReviewElement(result[index], subDiv);
    }
}

function createReviewElement(review, container) {
    const div1 = document.createElement("div");
    div1.classList.add("flex", "flex-col", "gap-3", "rounded-md", "p-4","bg-[#303135]");
    div1.id = "tr" + review.feed_id;
    container.append(div1);

    const input1 = document.createElement("input");
    input1.type = "hidden";
    input1.id = review.feed_id;
    input1.value = review.title;
    div1.append(input1);

    const div2 = document.createElement("div");
    div2.classList.add("flex", "justify-between", "items-center");
    div2.id = "div2" + review.feed_id;
    div1.append(div2);

    const div22 = document.createElement("div");
    div22.classList.add("flex", "gap-2", "items-center");
    div22.id = "div22" + review.feed_id;
    div2.append(div22);

    const div3 = document.createElement("div");
    div3.classList.add("w-7", "h-7", "flex", "items-center", "justify-center", "rounded-full");
    div3.id = "randomColorDiv";
    div3.innerText = review.fname ? review.fname[0].toUpperCase() : "0";

    const randomColor = colors[Math.floor(Math.random() * colors.length)];
    div3.classList.add(randomColor);
    div22.append(div3);

    const span1 = document.createElement("span");
    span1.innerText = review.fname ? `${review.fname} ${review.lname}` : "User";
    div22.append(span1);

    const div4 = document.createElement("div");
    div4.classList.add("flex", "gap-1", "text-yellow-400");
    div4.id = "div4" + review.feed_id;
    div2.append(div4);

    for (let index2 = 0; index2 < 5; index2++) {
        const icon = document.createElement("ion-icon");
        icon.name = "star";
        icon.id = "icon" + index2 + review.feed_id;
        div4.append(icon);
    }

    for (let i = 4; i >= parseInt(review.stars); i--) {
        document.getElementById("icon" + i + review.feed_id).classList.add("text-gray-400");
    }

    const div5 = document.createElement("div");
    div5.id = "div5" + review.feed_id;
    div1.append(div5);

    const p1 = document.createElement("p");
    p1.classList.add("font-bold");
    p1.innerText = review.title;
    div5.append(p1);

    const p2 = document.createElement("p");
    p2.classList.add("ms-5", "text-gray-500");
    p2.innerText = review.feed;
    div5.append(p2);

    const div6 = document.createElement("div");
    div6.classList.add("flex", "justify-between");
    div6.id = "div6" + review.feed_id;
    div1.append(div6);

    const span2 = document.createElement("span");
    const dateObject = new Date(review.date);
    span2.innerText = dateObject.toISOString().slice(0, 10);
    div6.append(span2);

    const button1 = document.createElement("button");
    button1.classList.add("p-1", "px-2", "bg-red-500","text-[#303135]", "hover:bg-red-600", "rounded-md");
    button1.addEventListener("click", () => {
        openModal(review.feed_id, review.title);
    });
    button1.innerText = "Delete";
    div6.append(button1);
}