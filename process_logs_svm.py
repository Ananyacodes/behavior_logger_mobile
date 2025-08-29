import pandas as pd
from sklearn import svm
from sklearn.preprocessing import StandardScaler
import os

# Path to your CSV log file
CSV_PATH = 'app/src/main/assets/behavior_logs.csv'  # Update if needed

# Load data
def load_data(csv_path):
    df = pd.read_csv(csv_path)
    # Example: Use timestamp and source as features (hash is not useful for SVM)
    X = df[['timestamp', 'source']]
    # Convert 'source' to numeric
    X['source'] = X['source'].astype('category').cat.codes
    return X, df

# Train SVM for anomaly detection
def train_svm(X):
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)
    clf = svm.OneClassSVM(nu=0.05, kernel='rbf', gamma='auto')
    clf.fit(X_scaled)
    return clf, scaler

# Flag anomalies and delete logs
def process_and_flag(csv_path):
    X, df = load_data(csv_path)
    clf, scaler = train_svm(X)
    X_scaled = scaler.transform(X)
    preds = clf.predict(X_scaled)
    # -1 means anomaly
    if (preds == -1).any():
        print('Abnormal activity detected!')
    else:
        print('No abnormal activity detected.')
    # Always delete CSV after training
    os.remove(csv_path)
    print('CSV logs deleted.')

if __name__ == '__main__':
    process_and_flag(CSV_PATH)
