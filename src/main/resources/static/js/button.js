$('form').submit(function () {
    var button = $('#button');
    var oldValue = button.value;
    var isDisabled = true;

    button.attr('disabled', isDisabled);

    setTimeout(function () {
        button.value = oldValue;
        button.attr('disabled', !isDisabled);
    }, 5000)
});