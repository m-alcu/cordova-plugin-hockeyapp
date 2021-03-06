var exec = require('cordova/exec');

var hockeyapp = {
    start:function(success, failure, token) {
        exec(success, failure, "HockeyApp", "start", [ token ]);
    },
    feedback:function(success, failure, token) {
        exec(success, failure, "HockeyApp", "feedback", [ token ]);
    },
    saveException:function(success, failure, description) {
        exec(success, failure, "HockeyApp", "saveException", [ description ]);
    }    
};

module.exports = hockeyapp;
