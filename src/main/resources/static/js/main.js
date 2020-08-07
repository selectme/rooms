$('#offBtn').click(function(){
    $.ajax({
        url: '/rooms/' + roomId + '/switch-lamp?isOn=false',
        type: 'put',
        success: function(){
            $('#onBtn').show();
            $('#offBtn').hide();
        },
        error: function(){
            window.location.href = '/error';
        }
    });
});

$('#onBtn').click(function(){
    $.ajax({
        url: '/rooms/' + roomId + '/switch-lamp?isOn=true',
        type: 'put',
        success: function(){
            $('#offBtn').show();
            $('#onBtn').hide();
        },
        error: function(){
            window.location.href = '/error';
        }
    });
});

