package uwaterloo.cs446group7.increpeable.backend;

import android.util.Log;
import android.util.Pair;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import uwaterloo.cs446group7.increpeable.MainActivity;

import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.RealmQuery;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import io.realm.RealmObject;

public class RealmClient {
    Realm realm;
    App app;
    public RealmClient() {
        buildApp();
        authenticate();
    }
    public void finalize() {
        // Close realm transactions
        realm.close();

        // Log out of Database
        app.currentUser().logOutAsync(result -> {
            if (result.isSuccess()) {
                Log.v("Backend", "MongoDB user successfully logged out.");
            } else {
                Log.e("Backend", "MongoDB user failed to log out, error: " + result.getError());
            }
        });
    }

    // Todo: Finish CRUD & run tests
//    Task newTask = new Task("New Task 3");
//    FutureTask<String> task = new FutureTask(new CreateCallable<Task>(realm, newTask));
//    Thread t = new Thread(task);
//        t.start();
//        try {
//        String str = task.get();
//        System.out.println("DEBUG: " + str);
//    } catch (Exception e) {
//        System.out.println("DEBUG: future task failed" + e.getMessage());
//    }

    private void buildApp() {
        String appID = "increpeable-eixfd";
        app = new App(new AppConfiguration.Builder(appID)
                .build());
    }

    private void authenticate() {
        // login
        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                User user = app.currentUser();
                String partitionValue = "partition1";
                SyncConfiguration config = new SyncConfiguration.Builder(
                        user,
                        partitionValue)
                        .build();
                realm = Realm.getInstance(config);
            } else {
                Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
            }
        });
    }

    public class CreateCallable <T extends RealmObject> implements Callable {
        T object;
        Realm realm;

        public CreateCallable( Realm realm, T object) {
            this.realm = realm;
            this.object = object;
        }
        @Override
        public Object call() {
            realm.executeTransaction (transactionRealm -> {
                transactionRealm.insert(object);
            });
            return 0;
        }
    }

    public class ReadCallable <T extends RealmObject> implements Callable {
        Realm realm;
        Pair<String, String>[] criteria;
        Class<T> type;
        public ReadCallable(Realm realm, Pair<String, String>[] criteria, Class<T> type) {
            this.realm = realm;
            this.criteria = criteria;
            this.type = type;
        }

        @Override
        public Object call() {
//            RealmResults<T> results;
            RealmQuery<T> result = realm.where(type);
            for (int i = 0; i < criteria.length; i++) {
                result = result.equalTo(criteria[i].first, criteria[i].second);
            }
            return realm.copyFromRealm(result.findAll());
        }
    }

    public class UpdateCallable <T extends RealmObject> implements Callable {
        private Realm realm;
        private String object_id;
        private T object;
        private final Class<T> type;
        public UpdateCallable(Realm realm, T object, String object_id, Class<T> type) {
            this.realm = realm;
            this.object_id = object_id;
            this.object = object;
            this.type = type;
        }

        @Override
        public Object call() {
            realm.executeTransaction(transactionRealm -> {
                T objectFound = transactionRealm.where(type).equalTo("_id", object_id).findFirst();
                objectFound.deleteFromRealm();
                transactionRealm.insert(object);
            });
            return 0;
        }
    }

    public class DeleteCallable <T extends RealmObject> implements Callable {
        Realm realm;
        String object_id;
        Class<T> type;
        public DeleteCallable(Realm realm, String object_id, Class<T> type) {
            this.realm = realm;
            this.object_id = object_id;
            this.type = type;
        }

        @Override
        public Object call() {
            realm.executeTransaction(transactionRealm -> {
                T objectFound = transactionRealm.where(type).equalTo("_id", object_id).findFirst();
                objectFound.deleteFromRealm();
            });
            return 0;
        }
    }
}