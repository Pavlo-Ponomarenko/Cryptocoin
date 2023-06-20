var signature = null;

function form_transaction(sign) {
    var hash = document.getElementById('hash').value;
    var index = document.getElementById('index').value;
    var address = document.getElementById('address').value;
    var value = document.getElementById('value').value;
    var transaction = {
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
    var privateKey = document.getElementById('privateKey').value;
    var transaction = form_transaction(null);
    var signingForm = {
        privateKey: privateKey,
        transaction: transaction
    };
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/sign_transaction',
        data: JSON.stringify(signingForm),
        async: false,
        dataType: 'text',
        success: function(response) {
            signature = response;
            document.getElementById('sign_status').textContent = 'Signed';
            document.getElementById('sign_status').style.color = 'forestgreen';
            document.getElementById('submit_button').disabled = false;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('Error: ' + textStatus + errorThrown);
        },
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }});
}

function sendTransaction() {
    var key = document.getElementById('current_address').textContent;
    var signDTO = {
        signature: signature,
        key: key
    }
    var transaction = form_transaction(signDTO);
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/send_transaction',
        data: JSON.stringify(transaction),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }});
}