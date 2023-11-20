import com.adobe.api.LogService;
import com.adobe.api.impl.LogStdOut;
module impl {
    requires api;
    exports com.adobe.api.impl;
    provides LogService with LogStdOut; // helps in service locater
}
