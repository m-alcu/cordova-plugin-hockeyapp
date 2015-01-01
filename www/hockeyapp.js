var exec = require('cordova/exec');

var hockeyapp = {
    start:function(success, failure, token) {
        exec(success, failure, "HockeyApp", "start", [ token ]);
    },
    feedback:function(success, failure) {
        exec(success, failure, "HockeyApp", "feedback", []);
    }
    saveException:function(success, failure, description) {
        exec(success, failure, "HockeyApp", "saveException", [ description ]);
    }    
};

module.exports = hockeyapp;
