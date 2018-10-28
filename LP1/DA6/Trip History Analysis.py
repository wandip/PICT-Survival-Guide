
# coding: utf-8

# In[91]:


import numpy as np
import pandas as pd

from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split


# In[92]:


data = pd.read_csv("2010-capitalbikeshare-tripdata.csv")
data.head()


# In[93]:


data['Start date'] = pd.to_datetime(data['Start date']).values.astype(np.int64) // 10 ** 9
data['End date'] = pd.to_datetime(data['End date']).values.astype(np.int64) // 10 ** 9
data.head()


# In[94]:


data = data.drop(columns = ['Start station', 'End station', 'Bike number'])
data.head()


# In[95]:


X = data.iloc[:, 0:5]
X.head()


# In[96]:


y = data.iloc[:, 5]
y.head()


# In[100]:


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2)


# In[101]:


predicted = KNeighborsClassifier().fit(X_train, y_train).predict(X_test)
accuracy_score(y_test, predicted)


# In[107]:


KNeighborsClassifier().fit(X_train, y_train).predict([[61, 1284982882, 1284982943, 31209, 31209]])

