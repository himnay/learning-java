module com.org.jpms.app {
    requires com.org.jpms.api;

    // Declares that this module consumes a Greeter service; ServiceLoader
    // uses this to resolve provider modules (jpms-service) on the module
    // path without the app requiring the provider module directly.
    uses com.org.jpms.api.Greeter;
}
