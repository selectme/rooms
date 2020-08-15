$('#offBtn').click(function () {
    $.ajax({
        url: '/rooms/' + roomId + '/switch-lamp?isOn=false',
        type: 'put',
        success: function () {
            $('#onBtn').show();
            $('#offBtn').hide();
            $('#lightOn').hide();
            $('#lightOff').show();
        },
        error: function () {
            window.location.href = '/error';
        }
    });
});

$('#onBtn').click(function () {
    $.ajax({
        url: '/rooms/' + roomId + '/switch-lamp?isOn=true',
        type: 'put',
        success: function () {
            $('#offBtn').show();
            $('#onBtn').hide();
            $('#lightOff').hide();
            $('#lightOn').show();
        },
        error: function () {
            window.location.href = '/error';
        }
    });
});

setInterval(function () {
    $.ajax({
        url: '/rooms/' + roomId + '/lamp',
        type: 'get',
        success: function (data) {
            if(data){
                $('#offBtn').show();
                $('#onBtn').hide();
                $('#lightOff').hide();
                $('#lightOn').show();
            }else {
                $('#onBtn').show();
                $('#offBtn').hide();
                $('#lightOn').hide();
                $('#lightOff').show();
            }
        },
        error: function () {
            window.location.href = '/error';
        }
    });
}, 1000);


