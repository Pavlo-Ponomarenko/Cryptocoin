var signature = null;

function form_transaction(sign) {
    var hash = document.getElementById('hash').value;
    var index = document.getElementById('index').value;
    var address = document.getElementById('address').value;
    var value = document.getElementById('value').value;
    var privateKey = document.getElementById('privateKey').value;
    var transaction = {
        privateKey: privateKey.trim(),
        vins: [
            {
                hash: hash.trim(),
                index: index.trim(),
                signature: sign
            }
        ],
        vouts: [
            {
                value: value.trim(),
                address: address.trim()
            }
        ]
    };
    return transaction;
}

function sign() {
    var transaction = form_transaction(null);
    var answer = $.ajax({
        type: "POST",
        url: 'http://localhost:8080/sign_transaction',
        data: JSON.stringify(transaction),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }});
    signature = answer.responseText;
    document.getElementById('sign_status').style.color = 'forestgreen';
    document.getElementById('submit_button').disabled = false;
}

function submit() {
    var transaction = form_transaction(signature);
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/send_transaction',
        data: JSON.stringify(transaction),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }});
    document.location = 'http://localhost:8080/wallet';
}