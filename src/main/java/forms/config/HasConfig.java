package forms.config;



// NOTE TO ALL COMPONENTS! remember to render your initialization javascript so the config data will be sent over each time.
public interface HasConfig<T extends Config> {

    T getConfig();

    // force each widget to render it's config.
}
