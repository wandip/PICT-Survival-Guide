
# coding: utf-8

# In[24]:


import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score


# In[25]:


data = pd.read_csv("diabetes.csv")
data.head()


# In[26]:


X = data.iloc[:,0:8]
X.head()


# In[35]:


y = data.iloc[:, 8]
y.head()


# In[39]:


data.describe()


# In[40]:


data.info()


# In[33]:


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)


# In[34]:


predicted = GaussianNB().fit(X_train, y_train).predict(X_test)
accuracy_score(y_test, predicted)

